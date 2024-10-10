package ru.vafeen.universityschedule.application_main

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.vafeen.universityschedule.noui.permissions.RequestNotificationPermission
import ru.vafeen.universityschedule.ui.components.screens.MainScreen
import ru.vafeen.universityschedule.ui.components.screens.SettingsScreen
import ru.vafeen.universityschedule.ui.components.ui_utils.CheckUpdateAndOpenBottomSheetIfNeed
import ru.vafeen.universityschedule.ui.components.viewModels.MainActivityViewModel
import ru.vafeen.universityschedule.ui.navigation.Screen
import ru.vafeen.universityschedule.ui.theme.MainTheme
import ru.vafeen.universityschedule.ui.theme.ScheduleTheme


class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModel()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RequestNotificationPermission()
            MainTheme {
                if (!viewModel.updateIsShowed)
                    CheckUpdateAndOpenBottomSheetIfNeed(
                        networkRepository = viewModel.networkRepository
                    ) {
                        viewModel.updateIsShowed = true
                    }

                val navController = rememberNavController()
                Column(
                    modifier = Modifier
                        .background(ScheduleTheme.colors.singleTheme)
                ) {
                    NavHost(
                        navController = navController, startDestination = Screen.Main.route
                    ) {
                        composable(Screen.Main.route) {
                            MainScreen(
                                navController = navController,
                            )
                        }
                        composable(Screen.Settings.route) {
                            SettingsScreen(
                                navController = navController,
                            )
                        }
                    }
                }
            }
        }
    }
}