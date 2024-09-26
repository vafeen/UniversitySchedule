package ru.vafeen.universityschedule.ui.main

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.vafeen.universityschedule.noui.notifications.NotificationService
import ru.vafeen.universityschedule.noui.permissions.RequestNotificationPermission
import ru.vafeen.universityschedule.ui.components.screens.MainScreen
import ru.vafeen.universityschedule.ui.components.screens.SettingsScreen
import ru.vafeen.universityschedule.ui.components.ui_utils.CheckUpdateAndOpenBottomSheetIfNeed
import ru.vafeen.universityschedule.ui.components.viewModels.MainActivityViewModel
import ru.vafeen.universityschedule.ui.components.viewModels.factories.provider.ViewModelsFactoryProvider
import ru.vafeen.universityschedule.ui.navigation.Screen
import ru.vafeen.universityschedule.ui.theme.MainTheme
import ru.vafeen.universityschedule.ui.theme.ScheduleTheme
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModelsFactoryProvider: ViewModelsFactoryProvider

    @Inject
    lateinit var notificationService: NotificationService

    private val viewModel: MainActivityViewModel by viewModels<MainActivityViewModel> {
        viewModelsFactoryProvider.mainActivityViewModelFactory
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        notificationService.showNotification("title", "text")
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
                                viewModel = viewModel(factory = viewModelsFactoryProvider.mainScreenViewModelFactory)
                            )
                        }
                        composable(Screen.Settings.route) {
                            SettingsScreen(
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