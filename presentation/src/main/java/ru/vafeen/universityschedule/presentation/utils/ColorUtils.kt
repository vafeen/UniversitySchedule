package ru.vafeen.universityschedule.presentation.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils
import kotlin.random.Random

/**
 * Функция для получения подходящего цвета текста в зависимости от яркости фона.
 * Если фон темный, возвращается белый цвет текста, если светлый — черный.
 *
 * @param color Цвет фона, для которого выбирается подходящий цвет текста.
 * @return Подходящий цвет для текста (черный или белый).
 */
internal fun Color.suitableColor(): Color =
    if (this.isDark()) Color.White else Color.Black

/**
 * Функция для проверки, является ли цвет темным.
 * Темный цвет определяется на основе его яркости, рассчитываемой с помощью формулы luminance.
 * Если яркость меньше 0.4, цвет считается темным.
 *
 * @return Истинно, если цвет темный, ложь — если светлый.
 */
internal fun Color.isDark(): Boolean = ColorUtils.calculateLuminance(this.toArgb()) < 0.4

/**
 * Функция для генерации случайного цвета.
 * Создает случайный цвет, где каждый канал (красный, зеленый, синий) выбирается случайным образом.
 * Альфа-канал (прозрачность) всегда равен 255 (полностью непрозрачный).
 *
 * @return Случайный цвет.
 */
internal fun generateRandomColor(): Color = Color(
    red = Random.nextInt(256),
    green = Random.nextInt(256),
    blue = Random.nextInt(256),
    alpha = 255
)
