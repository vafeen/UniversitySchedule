package ru.vafeen.universityschedule.presentation.components.ui_utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.vafeen.universityschedule.presentation.R
import ru.vafeen.universityschedule.presentation.theme.FontSize
import ru.vafeen.universityschedule.presentation.theme.Theme

@Composable
internal fun UpdateProgress(percentage: Float) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .background(Theme.colors.buttonColor),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextForThisTheme(
            modifier = Modifier.padding(vertical = 3.dp),
            text = "${stringResource(id = R.string.updating)} ${(percentage * 100).toInt()}%",
            fontSize = FontSize.medium19
        )
        LinearProgressIndicator(
            color = Theme.colors.oppositeTheme,
            trackColor = Theme.colors.singleTheme,
            progress = { percentage.toFloat() },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}