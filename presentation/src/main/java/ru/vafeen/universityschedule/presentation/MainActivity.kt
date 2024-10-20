package ru.vafeen.universityschedule.presentation

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
import ru.vafeen.universityschedule.presentation.components.permissions.RequestNotificationPermission
import ru.vafeen.universityschedule.presentation.components.screens.MainScreen
import ru.vafeen.universityschedule.presentation.components.screens.SettingsScreen
import ru.vafeen.universityschedule.presentation.components.ui_utils.CheckUpdateAndOpenBottomSheetIfNeed
import ru.vafeen.universityschedule.presentation.components.viewModels.MainActivityViewModel
import ru.vafeen.universityschedule.presentation.navigation.Screen
import ru.vafeen.universityschedule.presentation.theme.MainTheme
import ru.vafeen.universityschedule.presentation.theme.Theme


class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModel()


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewModel.registerGeneralExceptionCallback(context = this)
        setContent {
            RequestNotificationPermission()
            MainTheme {
                if (!viewModel.updateIsShowed)
                    CheckUpdateAndOpenBottomSheetIfNeed {
                        viewModel.updateIsShowed = true
                    }

                val navController = rememberNavController()
                Column(
                    modifier = Modifier
                        .background(Theme.colors.singleTheme)
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