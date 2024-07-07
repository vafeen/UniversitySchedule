package ru.vafeen.universityschedule.ui.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.vafeen.universityschedule.ui.components.screens.MainScreen
import ru.vafeen.universityschedule.ui.components.screens.SettingsScreen
import ru.vafeen.universityschedule.ui.components.viewModels.MainScreenViewModel

@Composable
fun AppNavHost(context: Context) {
    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = Screen.Main.route
    ) {
        composable(Screen.Main.route) {
            MainScreen(
                navController = navController,
                viewModel = viewModel(modelClass = MainScreenViewModel::class.java),
                context = context
            )
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController = navController, context = context)
        }
    }
}