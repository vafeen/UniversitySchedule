package ru.vafeen.universityschedule.ui.components

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.vafeen.universityschedule.R
import ru.vafeen.universityschedule.ui.theme.FontSize

@Composable
fun WeekDay(context: Context, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 80.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextForThisTheme(
            text = context.getString(R.string.holiday),
            fontSize = FontSize.huge,
            modifier = Modifier
        )
    }
}