package ru.vafeen.universityschedule.presentation.components.bottom_sheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.vafeen.universityschedule.presentation.components.ui_utils.TextForThisTheme
import ru.vafeen.universityschedule.presentation.components.viewModels.MainActivityViewModel
import ru.vafeen.universityschedule.presentation.theme.FontSize
import ru.vafeen.universityschedule.presentation.theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NewVersionInfoBottomSheet(
    viewModel: MainActivityViewModel,
    onDismissRequest: () -> Unit,
) {
    val state =
        rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val settings by viewModel.settings.collectAsState()
    ModalBottomSheet(
        sheetState = state,
        onDismissRequest = onDismissRequest,
        containerColor = Theme.colors.buttonColor,
    ) {
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(15.dp)
        ) {
            TextForThisTheme(
                modifier = Modifier.fillMaxWidth(),
                text = settings.releaseBody,
                fontSize = FontSize.big22
            )
        }
    }
}