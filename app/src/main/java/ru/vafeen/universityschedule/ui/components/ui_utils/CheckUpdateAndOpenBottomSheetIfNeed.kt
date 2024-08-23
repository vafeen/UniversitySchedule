package ru.vafeen.universityschedule.ui.components.ui_utils

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import ru.vafeen.universityschedule.ui.components.bottom_sheet.UpdaterBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckUpdateAndOpenBottomSheetIfNeed(isUpdateNeededCallback: suspend () -> Boolean) {
    val bottomSheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isUpdateNeeded by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = null) {
        isUpdateNeeded = isUpdateNeededCallback()
    }

    if (isUpdateNeeded)
        UpdaterBottomSheet(state = bottomSheetState) {
            isUpdateNeeded = false
        }
}