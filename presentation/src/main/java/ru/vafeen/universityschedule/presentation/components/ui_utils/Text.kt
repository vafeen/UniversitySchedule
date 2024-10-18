package ru.vafeen.universityschedule.presentation.components.ui_utils

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import ru.vafeen.universityschedule.presentation.theme.Theme

const val undefined = "undefined"

@Composable
internal fun TextForThisTheme(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = TextUnit.Unspecified,
    textAlign: TextAlign? = null,
) {
    Text(
        text = text,
        modifier = modifier,
        color = Theme.colors.oppositeTheme,
        fontSize = fontSize,
        textAlign = textAlign,
    )
}

@Composable
internal fun TextForOppositeTheme(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = TextUnit.Unspecified,
) {
    Text(
        text = text,
        modifier = modifier,
        color = Theme.colors.singleTheme,
        fontSize = fontSize,
    )
}