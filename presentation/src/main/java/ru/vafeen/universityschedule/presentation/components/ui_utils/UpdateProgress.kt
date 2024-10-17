package ru.vafeen.universityschedule.presentation.components.ui_utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.vafeen.universityschedule.data.R
import ru.vafeen.universityschedule.presentation.theme.FontSize
import ru.vafeen.universityschedule.presentation.theme.ScheduleTheme

@Composable
fun UpdateProgress(percentage: MutableState<ru.vafeen.universityschedule.data.network.downloader.Progress>) {
    val value = percentage.value.let {
        100 * it.totalBytesRead / (it.contentLength.let { cl ->
            if (cl.toFloat() == 0f) 1 else cl
        })
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .background(ScheduleTheme.colors.buttonColor),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextForThisTheme(
            modifier = Modifier.padding(vertical = 3.dp),
            text = "${stringResource(id = R.string.updating)} ${value}%",
            fontSize = FontSize.medium19
        )
        LinearProgressIndicator(
            color = ScheduleTheme.colors.oppositeTheme,
            trackColor = ScheduleTheme.colors.singleTheme,
            progress = { value.toFloat() / 100 },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}