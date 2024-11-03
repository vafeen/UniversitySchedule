package ru.vafeen.universityschedule.presentation.components.ui_utils

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import ru.vafeen.universityschedule.domain.models.Release
import ru.vafeen.universityschedule.domain.utils.getVersionName
import ru.vafeen.universityschedule.presentation.components.bottom_sheet.UpdaterBottomSheet
import ru.vafeen.universityschedule.presentation.components.viewModels.MainActivityViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CheckUpdateAndOpenBottomSheetIfNeed(
    viewModel: MainActivityViewModel,
    onDismissRequest: (Boolean) -> Unit,
) {
    val context = LocalContext.current
    val versionName = context.getVersionName()
    val bottomSheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isUpdateNeeded by remember {
        mutableStateOf(false)
    }
    var release: Release? by remember {
        mutableStateOf(null)
    }
    LaunchedEffect(key1 = null) {
        release = viewModel.getLatestReleaseUseCase.use()
        if (release != null && versionName != null &&
            release?.tagName?.substringAfter("v") != versionName
        )
            isUpdateNeeded = true
    }

    if (isUpdateNeeded)
        release?.let { releaseParam ->
            UpdaterBottomSheet(
                release = releaseParam,
                state = bottomSheetState,
            ) {
                isUpdateNeeded = false
                onDismissRequest(it)
            }
        }
}