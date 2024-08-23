package ru.vafeen.universityschedule.ui.components.bottom_sheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdaterBottomSheet(state: SheetState, onDismissRequest: () -> Unit) {
    ModalBottomSheet(
        sheetState = state,
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier.size(600.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "close", modifier = Modifier.clickable {
                onDismissRequest()
            })
        }
    }
}