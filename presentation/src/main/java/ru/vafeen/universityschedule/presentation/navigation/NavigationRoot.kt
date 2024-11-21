package ru.vafeen.universityschedule.presentation.navigation


import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.vafeen.universityschedule.domain.utils.getMainColorForThisTheme
import ru.vafeen.universityschedule.presentation.components.bottom_bar.BottomBar
import ru.vafeen.universityschedule.presentation.components.bottom_sheet.NewVersionInfoBottomSheet
import ru.vafeen.universityschedule.presentation.components.screens.MainScreen
import ru.vafeen.universityschedule.presentation.components.screens.SettingsScreen
import ru.vafeen.universityschedule.presentation.components.ui_utils.CheckUpdateAndOpenBottomSheetIfNeed
import ru.vafeen.universityschedule.presentation.components.ui_utils.UpdateProgress
import ru.vafeen.universityschedule.presentation.components.viewModels.MainActivityViewModel
import ru.vafeen.universityschedule.presentation.theme.Theme

@Composable
internal fun NavigationRoot(
    viewModel: MainActivityViewModel,
) {
    val isUpdateInProcess by viewModel.isUpdateInProcessFlow.collectAsState(false)
    val downloadedPercentage by viewModel.percentageFlow.collectAsState(0f)
    val dark = isSystemInDarkTheme()
    val defaultColor = Theme.colors.mainColor
    val settings by viewModel.settings.collectAsState()
    val mainColor by remember {
        derivedStateOf {
            settings.getMainColorForThisTheme(isDark = dark) ?: defaultColor
        }
    }
    var updateIsShowed by remember { mutableStateOf(false) }
    if (!updateIsShowed) CheckUpdateAndOpenBottomSheetIfNeed(viewModel = viewModel) {
        updateIsShowed = true
    }
    if (updateIsShowed && settings.lastDemonstratedVersion < viewModel.versionCode && settings.releaseBody != "") {
        NewVersionInfoBottomSheet(viewModel = viewModel) {
            viewModel.saveSettingsToSharedPreferences(
                settings.copy(lastDemonstratedVersion = viewModel.versionCode)
            )
        }
    }
    Scaffold(containerColor = Theme.colors.singleTheme,
        bottomBar = {
            val selectedScreen by viewModel.currentScreen.collectAsState()
            BottomBar(
                selectedScreen = selectedScreen,
                bottomBarNavigator = viewModel,
                containerColor = mainColor,
            )
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(Theme.colors.singleTheme)
        ) {
            NavHost(
                navController = viewModel.navController
                    ?: throw Exception("navController is null "),
                startDestination = viewModel.startScreen.route
            ) {
                composable(Screen.Main.route) {
                    MainScreen(viewModel)
                }
                composable(Screen.Settings.route) {
                    SettingsScreen(viewModel)
                }
            }
            if (isUpdateInProcess) UpdateProgress(percentage = downloadedPercentage)
        }
    }
}