package ru.vafeen.universityschedule.presentation.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils
import kotlin.random.Random

/**
 * Getting suitable color
 * @param Color [For color which we will get a suitable color]
 */
internal fun Color.suitableColor(): Color =
    if (this.isDark()) Color.White else Color.Black

internal fun Color.isDark(): Boolean = ColorUtils.calculateLuminance(this.toArgb()) < 0.4

internal fun generateRandomColor(): Color = Color(
    red = Random.nextInt(256),
    green = Random.nextInt(256),
    blue = Random.nextInt(256),
    alpha = 255
)
