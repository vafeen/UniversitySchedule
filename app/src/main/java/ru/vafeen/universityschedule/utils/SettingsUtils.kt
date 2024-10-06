package ru.vafeen.universityschedule.utils

import android.content.SharedPreferences
import androidx.compose.ui.graphics.Color
import ru.vafeen.universityschedule.noui.shared_preferences.SharedPreferencesValue
import ru.vafeen.universityschedule.noui.shared_preferences.saveInOrRemoveFromSharedPreferences
import ru.vafeen.universityschedule.ui.components.Settings

fun SharedPreferences.save(
    settings: Settings,
) = saveInOrRemoveFromSharedPreferences {
    putString(SharedPreferencesValue.Settings.key, settings.toJsonString())
}


fun Settings.getMainColorForThisTheme(isDark: Boolean): Color? =
    if (isDark) darkThemeColor else lightThemeColor