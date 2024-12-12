package ru.vafeen.universityschedule.presentation.components.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.vafeen.universityschedule.domain.models.Settings
import ru.vafeen.universityschedule.domain.network.service.ApkDownloader
import ru.vafeen.universityschedule.domain.network.service.SettingsManager
import ru.vafeen.universityschedule.domain.scheduler.SchedulerAPIMigrationManager
import ru.vafeen.universityschedule.domain.usecase.network.GetLatestReleaseUseCase
import ru.vafeen.universityschedule.domain.utils.getVersionCode
import ru.vafeen.universityschedule.presentation.navigation.BottomBarNavigator
import ru.vafeen.universityschedule.presentation.navigation.Screen
import ru.vafeen.universityschedule.presentation.utils.Link
import ru.vafeen.universityschedule.presentation.utils.copyTextToClipBoard
import kotlin.system.exitProcess


internal class MainActivityViewModel(
    val getLatestReleaseUseCase: GetLatestReleaseUseCase,
    apkDownloader: ApkDownloader,
    context: Context,
    private val schedulerAPIMigrationManager: SchedulerAPIMigrationManager,
    private val settingsManager: SettingsManager
) : ViewModel(), BottomBarNavigator {
    val isUpdateInProcessFlow = apkDownloader.isUpdateInProcessFlow
    val percentageFlow = apkDownloader.percentageFlow

    val settings = settingsManager.settingsFlow

    fun saveSettingsToSharedPreferences(saving: (Settings) -> Settings) {
        settingsManager.save(saving)
    }


    suspend fun callSchedulerAPIMigration() {
        if (!settings.value.isMigrationFromAlarmManagerToWorkManagerSuccessful) {
            schedulerAPIMigrationManager.migrate()
            saveSettingsToSharedPreferences {
                it.copy(isMigrationFromAlarmManagerToWorkManagerSuccessful = true)
            }
        }
    }

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
        registerGeneralExceptionCallback(context = context)
    }

    val startScreen = Screen.Main
    override var navController: NavHostController? = null
    private val _currentScreen = MutableStateFlow(Screen.Main)
    override val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    fun emitCurrentScreen() {
        viewModelScope.launch(Dispatchers.Main) {
            navController?.currentBackStackEntryFlow?.collect {
                _currentScreen.emit(
                    Screen.valueOf(
                        it.destination.route.toString()
                    )
                )
            }
        }
    }

    override fun back() {
        navController?.popBackStack()
        emitCurrentScreen()
    }

    override fun navigateTo(screen: Screen) {
        if (screen != Screen.Main)
            navController?.navigate(screen.route)
        else navController?.popBackStack()
        emitCurrentScreen()
    }


    val versionCode = context.getVersionCode()
}