package ru.vafeen.universityschedule.ui.components.ui_utils

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ru.vafeen.universityschedule.ui.theme.ScheduleTheme

@Composable
fun DefaultDialog(
    onDismissRequest: () -> Unit,
    content: @Composable (ColumnScope.(Dp) -> Unit),
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            border = androidx.compose.foundation.BorderStroke(
                width = 2.dp,
                ScheduleTheme.colors.oppositeTheme
            )
        ) { content(10.dp) }
    }
}