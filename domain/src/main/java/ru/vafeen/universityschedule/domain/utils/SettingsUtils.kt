package ru.vafeen.universityschedule.domain.utils

import android.content.SharedPreferences
import androidx.compose.ui.graphics.Color
import ru.vafeen.universityschedule.domain.shared_preferences.SharedPreferencesValue
import ru.vafeen.universityschedule.domain.shared_preferences.saveInOrRemoveFromSharedPreferences

fun SharedPreferences.save(
    settings: ru.vafeen.universityschedule.domain.Settings,
) = saveInOrRemoveFromSharedPreferences {
    putString(SharedPreferencesValue.Settings.key, settings.toJsonString())
}


fun ru.vafeen.universityschedule.domain.Settings.getMainColorForThisTheme(isDark: Boolean): Color? =
    if (isDark) darkThemeColor else lightThemeColor