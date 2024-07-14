package ru.vafeen.universityschedule.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils

/**
 * Getting suitable color
 * @param Color [For color which we will get a suitable color]
 */
fun Color.suitableColor(): Color =
    if (this.isDark()) Color.White else Color.Black

fun Color.isDark(): Boolean = ColorUtils.calculateLuminance(this.toArgb()) < 0.4
