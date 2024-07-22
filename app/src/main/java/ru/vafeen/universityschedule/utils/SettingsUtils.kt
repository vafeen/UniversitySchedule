package ru.vafeen.universityschedule.utils

import androidx.compose.ui.graphics.Color
import com.google.gson.Gson
import ru.vafeen.universityschedule.ui.components.Settings

fun Settings.save(
    sharedPreferences: SharedPreferences
): Settings {
    sharedPreferences.saveInOrRemoveFromSharedPreferences {
        putString(SharedPreferencesValue.Settings.key, this@save.toJsonString())
    }
    return this
}

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

fun Settings.getMainColorForThisTheme(isDark: Boolean): Color? =
    if (isDark) darkThemeColor else lightThemeColor