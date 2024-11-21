package ru.vafeen.universityschedule.presentation.components.viewModels

import android.content.Context
import android.content.SharedPreferences
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
import ru.vafeen.universityschedule.domain.usecase.network.GetLatestReleaseUseCase
import ru.vafeen.universityschedule.domain.utils.getSettingsOrCreateIfNull
import ru.vafeen.universityschedule.domain.utils.getVersionCode
import ru.vafeen.universityschedule.domain.utils.save
import ru.vafeen.universityschedule.presentation.navigation.BottomBarNavigator
import ru.vafeen.universityschedule.presentation.navigation.Screen
import ru.vafeen.universityschedule.presentation.utils.Link
import ru.vafeen.universityschedule.presentation.utils.copyTextToClipBoard
import ru.vafeen.universityschedule.presentation.utils.navigateOnScreenWithSingleScreenRule
import kotlin.system.exitProcess


internal class MainActivityViewModel(
    val getLatestReleaseUseCase: GetLatestReleaseUseCase,
    private val sharedPreferences: SharedPreferences,
    apkDownloader: ApkDownloader,
    context: Context,
) : ViewModel(), BottomBarNavigator {
    val isUpdateInProcessFlow = apkDownloader.isUpdateInProcessFlow
    val percentageFlow = apkDownloader.percentageFlow
    private val _settings =
        MutableStateFlow(sharedPreferences.getSettingsOrCreateIfNull())
    val settings = _settings.asStateFlow()
    private val spListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            viewModelScope.launch(Dispatchers.IO) {
                _settings.emit(sharedPreferences.getSettingsOrCreateIfNull())
            }
        }

    fun saveSettingsToSharedPreferences(settings: Settings) {
        sharedPreferences.save(settings = settings)
    }

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(spListener)
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

    override var navController: NavHostController? = null
    private val _currentScreen = MutableStateFlow(Screen.Main)
    override val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()
    suspend fun emitLastEntry() {
        _currentScreen.emit(
            Screen.valueOf(
                navController?.currentDestination?.route ?: ""
            )
        )
    }

    override fun back() = navigate { popBackStack() }
    override fun navigateTo(screen: Screen) =
        navigate { navigateOnScreenWithSingleScreenRule(screen) }
    private fun navigate(navigate: NavHostController.() -> Unit) {
        navController?.apply {
            navigate()
            viewModelScope.launch(Dispatchers.Main) {
                emitLastEntry()
            }
        }
    }

    val versionCode = context.getVersionCode()
    override fun onCleared() {
        super.onCleared()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(spListener)
    }
}