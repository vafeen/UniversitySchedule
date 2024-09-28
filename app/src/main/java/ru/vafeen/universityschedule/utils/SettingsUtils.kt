package ru.vafeen.universityschedule.utils

import androidx.compose.ui.graphics.Color
import ru.vafeen.universityschedule.noui.shared_preferences.SharedPreferences
import ru.vafeen.universityschedule.noui.shared_preferences.SharedPreferencesValue
import ru.vafeen.universityschedule.ui.components.Settings

fun Settings.save(
    sharedPreferences: SharedPreferences
): Settings {
    sharedPreferences.saveInOrRemoveFromSharedPreferences {
        putString(SharedPreferencesValue.Settings.key, this@save.toJsonString())
    }
    return this
}

fun Settings.getMainColorForThisTheme(isDark: Boolean): Color? =
    if (isDark) darkThemeColor else lightThemeColor