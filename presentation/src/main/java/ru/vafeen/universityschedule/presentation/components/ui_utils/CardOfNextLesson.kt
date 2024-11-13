package ru.vafeen.universityschedule.presentation.components.ui_utils


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.vafeen.universityschedule.presentation.theme.FontSize
import ru.vafeen.universityschedule.presentation.utils.suitableColor
import ru.vafeen.universityschedule.resources.R


@Composable
internal fun CardOfNextLesson(
    colorOfCard: Color,
    thisContent: @Composable (() -> Unit)
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        colors = CardDefaults.cardColors(containerColor = colorOfCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Text(
            text = stringResource(R.string.next_lesson),
            fontSize = FontSize.medium19,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = colorOfCard.suitableColor()
        )
        thisContent()
    }
}