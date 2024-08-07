package ru.vafeen.universityschedule.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.vafeen.universityschedule.ui.components.screens.MainScreen
import ru.vafeen.universityschedule.ui.components.screens.SettingsScreen
import ru.vafeen.universityschedule.ui.components.viewModels.factories.provider.ViewModelsFactoryProvider
import ru.vafeen.universityschedule.ui.navigation.Screen
import ru.vafeen.universityschedule.ui.theme.MainTheme
import ru.vafeen.universityschedule.ui.theme.ScheduleTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModelsFactoryProvider: ViewModelsFactoryProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val context = LocalContext.current

            MainTheme {
                val navController = rememberNavController()
                Column(modifier = Modifier.background(ScheduleTheme.colors.singleTheme)) {
                    NavHost(
                        navController = navController, startDestination = Screen.Main.route
                    ) {
                        composable(Screen.Main.route) {
                            MainScreen(
                                context = context,
                                navController = navController,
                                viewModel = viewModel(factory = viewModelsFactoryProvider.mainScreenViewModelFactory)
                            )
                        }
                        composable(Screen.Settings.route) {
                            SettingsScreen(
                                context = context,
                                navController = navController,
                                viewModel = viewModel(factory = viewModelsFactoryProvider.settingsScreenViewModelFactory),
                            )
                        }
                    }
                }
            }
        }
    }


}