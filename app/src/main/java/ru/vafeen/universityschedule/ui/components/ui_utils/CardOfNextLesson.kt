package ru.vafeen.universityschedule.ui.components.ui_utils


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CardOfNextLesson(thisContent: @Composable (() -> Unit)) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        colors = CardDefaults.cardColors(
//            containerColor = appTheme.dynamicMainColor
        )
    ) {
        Text(
            text = "следующая пара",
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally),
//            color = appTheme.colorOfText
        )
        thisContent()
    }
}