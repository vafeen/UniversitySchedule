package ru.vafeen.universityschedule.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.vafeen.universityschedule.presentation.components.screens.MainScreen
import ru.vafeen.universityschedule.presentation.components.screens.SettingsScreen

@Composable
internal fun NavigationRoot(
    viewModel: BottomBarNavigator,
    modifier: Modifier,
) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    NavHost(
        modifier = modifier,
        navController = viewModel.navController ?: rememberNavController(),
        startDestination = currentScreen.route
    ) {
        composable(Screen.Main.route) {
            MainScreen(viewModel)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(viewModel)
        }
    }
}