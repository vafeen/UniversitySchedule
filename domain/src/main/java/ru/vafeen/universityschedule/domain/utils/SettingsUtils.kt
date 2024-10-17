package ru.vafeen.universityschedule.domain.utils

import androidx.compose.ui.graphics.Color
import ru.vafeen.universityschedule.domain.Settings

fun Settings.getMainColorForThisTheme(isDark: Boolean): Color? =
    if (isDark) darkThemeColor else lightThemeColor