package ru.vafeen.universityschedule.presentation.components.ui_utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import ru.vafeen.universityschedule.presentation.theme.ScheduleTheme

const val undefined ="undefined"

@Composable
fun TextForThisTheme(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = TextUnit.Unspecified,
) {
    androidx.compose.material3.Text(
        text = text,
        modifier = modifier,
        color = ScheduleTheme.colors.oppositeTheme,
        fontSize = fontSize,
    )
}

@Composable
fun TextForOppositeTheme(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = TextUnit.Unspecified,
) {
    androidx.compose.material3.Text(
        text = text,
        modifier = modifier,
        color = ScheduleTheme.colors.singleTheme,
        fontSize = fontSize,
    )
}