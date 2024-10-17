package ru.vafeen.universityschedule.presentation.components.ui_utils

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.vafeen.universityschedule.data.R
import ru.vafeen.universityschedule.presentation.theme.FontSize

@Composable
internal fun WeekDay(context: Context, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextForThisTheme(
            text = context.getString(R.string.holiday),
            fontSize = FontSize.huge27,
            modifier = Modifier
        )
    }
}