package ru.vafeen.universityschedule.presentation.components.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import ru.vafeen.universityschedule.domain.models.Release
import ru.vafeen.universityschedule.domain.models.Settings
import ru.vafeen.universityschedule.domain.network.result.DownloadStatus
import ru.vafeen.universityschedule.domain.network.service.Refresher
import ru.vafeen.universityschedule.domain.network.service.SettingsManager
import ru.vafeen.universityschedule.domain.scheduler.SchedulerAPIMigrationManager
import ru.vafeen.universityschedule.domain.usecase.network.GetLatestReleaseUseCase
import ru.vafeen.universityschedule.domain.usecase.scheduler.RebootingRemindersUseCase
import ru.vafeen.universityschedule.domain.utils.getVersionCode
import ru.vafeen.universityschedule.domain.utils.getVersionName
import ru.vafeen.universityschedule.presentation.navigation.BottomBarNavigator
import ru.vafeen.universityschedule.presentation.navigation.Screen
import ru.vafeen.universityschedule.presentation.utils.Link
import ru.vafeen.universityschedule.presentation.utils.RefresherInfo
import ru.vafeen.universityschedule.presentation.utils.copyTextToClipBoard
import kotlin.system.exitProcess

/**
 * ViewModel для управления состоянием главной активности приложения.
 * Обрабатывает обновления, миграцию данных, навигацию и общие ошибки.
 *
 * @param getLatestReleaseUseCase UseCase для получения последней версии приложения.
 * @param refresher Сервис для скачивания и обновлений.
 * @param context Контекст приложения для работы с ресурсами и управления ошибками.
 * @param schedulerAPIMigrationManager Менеджер миграции API, который отвечает за перенос с AlarmManager на WorkManager.
 * @param settingsManager Менеджер для работы с настройками приложения.
 */
internal class MainActivityViewModel(
    private val getLatestReleaseUseCase: GetLatestReleaseUseCase,
    private val rebootingRemindersUseCase: RebootingRemindersUseCase,
    context: Context,
    private val schedulerAPIMigrationManager: SchedulerAPIMigrationManager,
    private val settingsManager: SettingsManager,
    private val refresher: Refresher,
) : ViewModel(), BottomBarNavigator {
    var release: Release? = null
        private set

    /**
     * Получает версию приложения.
     */
    val versionCode = context.getVersionCode()
    val versionName = context.getVersionName()

    suspend fun checkUpdates(): Release? {
        val localRelease = getLatestReleaseUseCase.invoke()
        return if (localRelease != null && versionName != null &&
            localRelease.tagName.substringAfter("v") != versionName
        ) {
            saveSettingsToSharedPreferences {
                it.copy(releaseBody = localRelease.body)
            }
            release = localRelease
            release
        } else null
    }

    fun update() {
        release?.let {
            viewModelScope.launch(Dispatchers.IO) {
                refresher.refresh(viewModelScope, it.apkUrl, RefresherInfo.APK_FILE_NAME)
            }
        }
    }

    /**
     * Поток состояния, который отслеживает процесс обновления.
     */
    val isUpdateInProcessFlow: SharedFlow<Boolean> = refresher.progressFlow.map {
        it !is DownloadStatus.Error && it !is DownloadStatus.Success
    }.shareIn(viewModelScope, SharingStarted.Lazily)

    /**
     * Поток состояния, который отслеживает процент выполнения обновления.
     */
    val percentageFlow: SharedFlow<Float> = refresher.progressFlow.map {
        when (it) {
            is DownloadStatus.InProgress -> it.percentage
            DownloadStatus.Success -> 100f
            else -> 0f
        }
    }.shareIn(viewModelScope, SharingStarted.Lazily)

    /**
     * Поток состояния, содержащий текущие настройки приложения.
     */
    val settings = settingsManager.settingsFlow

    /**
     * Функция для сохранения настроек в SharedPreferences.
     * Принимает функцию, изменяющую текущие настройки.
     *
     * @param saving Функция, изменяющая настройки.
     */
    fun saveSettingsToSharedPreferences(saving: (Settings) -> Settings) {
        settingsManager.save(saving)
    }

    /**
     * Запускает процесс миграции данных с AlarmManager на WorkManager, если миграция еще не была выполнена.
     * Обновляет настройки после успешной миграции.
     */
    suspend fun callSchedulerAPIMigration() {
        if (!settings.value.isMigrationFromAlarmManagerToWorkManagerSuccessful) {
            schedulerAPIMigrationManager.migrate()
            saveSettingsToSharedPreferences {
                it.copy(isMigrationFromAlarmManagerToWorkManagerSuccessful = true)
            }
        }
        if (!settings.value.isRemindersRebootedForVersion6_1_15) {
            rebootingRemindersUseCase.invoke()
            saveSettingsToSharedPreferences {
                it.copy(isRemindersRebootedForVersion6_1_15 = true)
            }
        }
    }

    /**
     * Регистрирует обработчик необработанных исключений для приложения.
     * В случае ошибки копирует информацию об ошибке в буфер обмена и завершает процесс.
     *
     * @param context Контекст приложения, используемый для работы с буфером обмена.
     */
    private fun registerGeneralExceptionCallback(context: Context) {
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            context.copyTextToClipBoard(
                label = "Error",
                text = "Contact us about this problem: ${Link.EMAIL}\n\n Exception in ${thread.name} thread\n${throwable.stackTraceToString()}"
            )
            Log.e("GeneralException", "Exception in thread ${thread.name}", throwable)
            exitProcess(0)
        }
    }

    init {
        // Регистрируем обработчик исключений при инициализации ViewModel.
        registerGeneralExceptionCallback(context = context)
    }

    /**
     * Экран, который должен отображаться при запуске приложения.
     */
    val startScreen = Screen.Main

    /**
     * Контроллер навигации, управляющий переходами между экранами.
     */
    override var navController: NavHostController? = null

    /**
     * Поток состояния, отслеживающий текущий экран приложения.
     */
    private val _currentScreen: MutableStateFlow<Screen> = MutableStateFlow(startScreen)
    override val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    /**
     * Эмитирует текущий экран на основе стека навигации.
     * Отслеживает изменения в backStack навигации и обновляет состояние текущего экрана.
     */
    private fun emitCurrentScreen() {
        viewModelScope.launch(Dispatchers.Main) {
            navController?.currentBackStackEntryFlow?.collect { backStackEntry ->
                val destination = backStackEntry.destination
                when {
                    destination.hasRoute(Screen.Main::class) -> _currentScreen.emit(Screen.Main)
                    destination.hasRoute(Screen.Settings::class) -> _currentScreen.emit(Screen.Settings)
                }
            }
        }
    }

    /**
     * Обрабатывает действие "Назад". Переходит на предыдущий экран в навигации.
     */
    override fun back() {
        navController?.popBackStack()
        emitCurrentScreen()
    }

    /**
     * Переходит на указанный экран.
     *
     * @param screen Целевой экран для навигации.
     */
    override fun navigateTo(screen: Screen) {
        if (screen != Screen.Main)
            navController?.navigate(screen)
        else navController?.popBackStack()
        emitCurrentScreen()
    }


}
