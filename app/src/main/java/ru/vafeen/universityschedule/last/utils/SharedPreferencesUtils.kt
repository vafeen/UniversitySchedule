package ru.vafeen.universityschedule.last.utils

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.vafeen.universityschedule.last.noui.shared_preferences.SharedPreferencesValue
import ru.vafeen.universityschedule.last.noui.shared_preferences.getFromSharedPreferences
import ru.vafeen.universityschedule.last.noui.shared_preferences.saveInOrRemoveFromSharedPreferences
import ru.vafeen.universityschedule.last.ui.components.Settings

fun SharedPreferences.getSettingsOrCreateIfNull(): Settings {
    val settings = getFromSharedPreferences {
        getString(SharedPreferencesValue.Settings.key, "").let {
            if (it != "") Gson().fromJson(it, Settings::class.java)
            else null
        }
    }
    return if (settings != null) settings
    else {
        val newSettings = Settings()
        saveInOrRemoveFromSharedPreferences {
            putString(SharedPreferencesValue.Settings.key, newSettings.toJsonString())
        }
        newSettings
    }
}