package ru.vafeen.universityschedule.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class ScheduleColors(
    val mainColor: Color,
    val singleTheme: Color,
    val oppositeTheme: Color,
    val buttonColor: Color,
)

val baseLightPalette = ScheduleColors(
    mainColor = Color(0xFF0E568D),
    singleTheme = Color.White,
    oppositeTheme = Color.Black,
    buttonColor = Color.Gray
)
val baseDarkPalette = baseLightPalette.copy(
    singleTheme = Color.Black,
    oppositeTheme = Color.White
)

@Composable
fun MainTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {

    val colors = if (!darkTheme) {
        baseLightPalette
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