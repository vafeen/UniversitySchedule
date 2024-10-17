package ru.vafeen.universityschedule.presentation.components.bottom_sheet

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import ru.vafeen.universityschedule.presentation.theme.updateAvailableColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NewVersionInfoBottomSheet(onDismissRequest: () -> Unit) {
    val state =
        rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        sheetState = state,
        onDismissRequest = onDismissRequest,
        containerColor = updateAvailableColor,
    ) {
    }
}