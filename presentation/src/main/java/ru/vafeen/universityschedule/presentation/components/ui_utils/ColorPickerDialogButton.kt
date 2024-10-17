package ru.vafeen.universityschedule.presentation.components.ui_utils


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.vafeen.universityschedule.presentation.theme.ScheduleTheme
import ru.vafeen.universityschedule.presentation.utils.suitableColor

@Composable
fun ColorPickerDialogButton(
    onClick: () -> Unit,
    color: Color,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    OutlinedButton(
        shape = RoundedCornerShape(10.dp),
        enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = color,
            contentColor = color.suitableColor(),
            disabledContentColor = color.suitableColor().copy(alpha = 0.5f),
            disabledContainerColor = color.copy(alpha = 0.5f),
        ),
        modifier = modifier,
        onClick = onClick,
        content = content,
        border = BorderStroke(1.dp, ScheduleTheme.colors.oppositeTheme)
    )
}