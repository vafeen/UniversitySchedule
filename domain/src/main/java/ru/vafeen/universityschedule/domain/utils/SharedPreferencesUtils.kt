package ru.vafeen.universityschedule.domain.utils

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.vafeen.universityschedule.domain.models.Settings


fun SharedPreferences.saveInOrRemoveFromSharedPreferences(save: SharedPreferences.Editor.() -> Unit) {
    edit().apply {
        save()
        apply()
    }
}

fun <T> SharedPreferences.getFromSharedPreferences(get: SharedPreferences.() -> T): T = get()

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

fun SharedPreferences.save(
    settings: Settings,
) = saveInOrRemoveFromSharedPreferences {
    putString(SharedPreferencesValue.Settings.key, settings.toJsonString())
}
