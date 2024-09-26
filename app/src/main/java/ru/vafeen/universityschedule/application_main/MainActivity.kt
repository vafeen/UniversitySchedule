package ru.vafeen.universityschedule.application_main

import android.annotation.SuppressLint
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
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.vafeen.universityschedule.noui.notifications.NotificationService
import ru.vafeen.universityschedule.noui.permissions.RequestNotificationPermission
import ru.vafeen.universityschedule.ui.components.screens.MainScreen
import ru.vafeen.universityschedule.ui.components.screens.SettingsScreen
import ru.vafeen.universityschedule.ui.components.ui_utils.CheckUpdateAndOpenBottomSheetIfNeed
import ru.vafeen.universityschedule.ui.components.viewModels.MainActivityViewModel
import ru.vafeen.universityschedule.ui.components.viewModels.MainScreenViewModel
import ru.vafeen.universityschedule.ui.components.viewModels.SettingsScreenViewModel
import ru.vafeen.universityschedule.ui.navigation.Screen
import ru.vafeen.universityschedule.ui.theme.MainTheme
import ru.vafeen.universityschedule.ui.theme.ScheduleTheme

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModel()
    private val mainScreenViewModel: MainScreenViewModel by viewModel()
    private val settingsScreenViewModel: SettingsScreenViewModel by viewModel()

    @SuppressLint("CoroutineCreationDuringComposition")
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
                                viewModel = mainScreenViewModel
                            )
                        }
                        composable(Screen.Settings.route) {
                            SettingsScreen(
                                navController = navController,
                                viewModel = settingsScreenViewModel,
                            )
                        }
                    }
                }
            }
        }
    }
}