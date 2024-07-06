package ru.vafeen.universityschedule.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class ScheduleColors(
    val mainColor: Color,
    val background: Color,
    val text: Color,
    val buttonColor: Color,
)

val basePalette = ScheduleColors(
    mainColor = Color(0xFF0E568D),
    background = Color.White,
    text = Color.Black,
    buttonColor = Color.DarkGray
)
val baseDarkPalette = basePalette.copy(background = Color.Black)

@Composable
fun MainTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {

    val colors = if (darkTheme) {
        basePalette
    } else {
        baseDarkPalette
    }

    CompositionLocalProvider(
        LocalColors provides colors,
        content = content
    )
}

object ScheduleTheme {
    val colors: ScheduleColors
        @Composable
        get() = LocalColors.current
}

val LocalColors = staticCompositionLocalOf<ScheduleColors> {
    error("Composition error")
}