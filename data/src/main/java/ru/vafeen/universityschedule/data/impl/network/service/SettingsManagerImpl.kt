//package ru.vafeen.universityschedule.data.impl.network.service
//
//import android.content.SharedPreferences
//import android.util.Log
//import com.google.gson.Gson
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//import ru.vafeen.universityschedule.domain.di.getSettingsOrCreateIfNull
//import ru.vafeen.universityschedule.domain.di.saveToSharedPreferences
//import ru.vafeen.universityschedule.domain.models.Settings
//import ru.vafeen.universityschedule.domain.network.service.SettingsManager
//import ru.vafeen.universityschedule.domain.utils.SharedPreferencesValue
//
//class SettingsManagerImpl(private val sharedPreferences: SharedPreferences) : SettingsManager {
//    private var settings = sharedPreferences.getSettingsOrCreateIfNull()
//    private val _settingsFlow = MutableStateFlow(settings)
//    override val settingsFlow: StateFlow<Settings> = _settingsFlow.asStateFlow()
//    init {
//        sharedPreferences.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
//            CoroutineScope(Dispatchers.IO).launch {
////                val settings = sharedPreferences.getSettingsOrCreateIfNull()
//                _settingsFlow.emit(settings)
//                Log.d("emit", "emited")
//            }
//        }
//    }
//
//    fun Settings.toStr(): String = "$notificationsAboutLesson $notesAboutLesson"
//
//    //    @Synchronized
//    override fun save(saving: (Settings) -> Unit) {
//        Log.d("save", "save ${settings.toStr()}")
//        saving(settings)
//        Log.d("save", "saved ${settings.toStr()}")
//        sharedPreferences.saveToSharedPreferences(settings)
//        Log.d("save", "saved to sp ${settings.toStr()}")
//    }
//
//}