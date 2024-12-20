package ru.vafeen.universityschedule.presentation.components.ui_utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.vafeen.universityschedule.domain.models.Release
import ru.vafeen.universityschedule.domain.utils.bytesToMBytes
import ru.vafeen.universityschedule.domain.utils.roundToOneDecimal
import ru.vafeen.universityschedule.presentation.theme.FontSize
import ru.vafeen.universityschedule.presentation.theme.updateAvailableColor
import ru.vafeen.universityschedule.resources.R

@Composable
fun UpdateAvailable(
    release: Release,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(updateAvailableColor)
            .clickable(onClick = onClick)
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.Absolute.SpaceEvenly
    ) {
        Icon(
            painter = painterResource(R.drawable.download),
            contentDescription = stringResource(R.string.update_app),
            tint = Color.White
        )
        Text(
            text = stringResource(R.string.update_app),
            color = Color.White,
            fontSize = FontSize.small17
        )
        Text(
            text = "${
                release.size.bytesToMBytes().roundToOneDecimal()
            } ${stringResource(R.string.mb)}",
            color = Color.White,
            fontSize = FontSize.small17
        )
    }
}