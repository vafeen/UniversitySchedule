package ru.vafeen.universityschedule.ui.components.ui_utils

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import ru.vafeen.universityschedule.network.parcelable.github_service.Release
import ru.vafeen.universityschedule.network.service.DownloadService
import ru.vafeen.universityschedule.network.service.GitHubDataService
import ru.vafeen.universityschedule.ui.components.bottom_sheet.UpdaterBottomSheet
import ru.vafeen.universityschedule.utils.getVersionName


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckUpdateAndOpenBottomSheetIfNeed(
    downloadService: DownloadService,
    gitHubDataService: GitHubDataService,
    onDismissRequest: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val bottomSheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isUpdateNeeded by remember {
        mutableStateOf(false)
    }
    var release: Release? by remember {
        mutableStateOf(null)
    }
    LaunchedEffect(key1 = null) {
        release = gitHubDataService.getLatestRelease().body()
        if (release != null && release?.tag_name?.substringAfter("v") == getVersionName(context = context))
            isUpdateNeeded = true
    }

    if (isUpdateNeeded)
        release?.let { releaseParam ->
            UpdaterBottomSheet(
                downloadService = downloadService,
                release = releaseParam,
                state = bottomSheetState,
            ) {
                isUpdateNeeded = false
                onDismissRequest(it)
            }
        }
}