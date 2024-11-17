package ru.vafeen.universityschedule.presentation.components.viewModels

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.vafeen.universityschedule.domain.models.Settings
import ru.vafeen.universityschedule.domain.network.service.ApkDownloader
import ru.vafeen.universityschedule.domain.usecase.network.GetLatestReleaseUseCase
import ru.vafeen.universityschedule.domain.utils.getSettingsOrCreateIfNull
import ru.vafeen.universityschedule.domain.utils.save
import ru.vafeen.universityschedule.presentation.utils.Link
import ru.vafeen.universityschedule.presentation.utils.copyTextToClipBoard
import kotlin.system.exitProcess


internal class MainActivityViewModel(
    val getLatestReleaseUseCase: GetLatestReleaseUseCase,
    private val sharedPreferences: SharedPreferences,
    apkDownloader: ApkDownloader,
) : ViewModel() {
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


    fun registerGeneralExceptionCallback(context: Context) {
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            context.copyTextToClipBoard(
                label = "Error",
                text = "Contact us about this problem: ${Link.EMAIL}\n\n Exception in ${thread.name} thread\n${throwable.stackTraceToString()}"
            )
            Log.e("GeneralException", "Exception in thread ${thread.name}", throwable)
            exitProcess(0)
        }
    }

    override fun onCleared() {
        super.onCleared()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(spListener)
    }
}