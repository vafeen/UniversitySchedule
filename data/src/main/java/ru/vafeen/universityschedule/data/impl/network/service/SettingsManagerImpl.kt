package ru.vafeen.universityschedule.data.impl.network.service

import android.content.SharedPreferences
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.vafeen.universityschedule.domain.models.Settings
import ru.vafeen.universityschedule.domain.network.service.SettingsManager
import ru.vafeen.universityschedule.domain.utils.getSettingsOrCreateIfNull
import ru.vafeen.universityschedule.domain.utils.save

class SettingsManagerImpl(private val sharedPreferences: SharedPreferences) : SettingsManager {
    private var settings = sharedPreferences.getSettingsOrCreateIfNull()
    private val _settingsFlow = MutableStateFlow(settings)
    override val settingsFlow: StateFlow<Settings> = _settingsFlow.asStateFlow()

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
            CoroutineScope(Dispatchers.IO).launch {
                settings = sharedPreferences.getSettingsOrCreateIfNull()
                _settingsFlow.emit(settings)
                Log.d("emit", "emited")
            }
        }
    }

    fun Settings.toStr(): String = "$notificationsAboutLesson $notesAboutLesson"

    @Synchronized
    override fun save(settings: Settings) {
        Log.d("save", "save ${settings.toStr()}")
        sharedPreferences.save(settings)
        Log.d("save", "saved to sp ${settings.toStr()}")
    }
}