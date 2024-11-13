package ru.vafeen.universityschedule.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.vafeen.universityschedule.domain.utils.getMainColorForThisTheme
import ru.vafeen.universityschedule.domain.utils.getVersionCode
import ru.vafeen.universityschedule.presentation.components.bottom_bar.BottomBar
import ru.vafeen.universityschedule.presentation.components.bottom_sheet.NewVersionInfoBottomSheet
import ru.vafeen.universityschedule.presentation.components.permissions.RequestNotificationPermission
import ru.vafeen.universityschedule.presentation.components.screens.MainScreen
import ru.vafeen.universityschedule.presentation.components.screens.SettingsScreen
import ru.vafeen.universityschedule.presentation.components.ui_utils.CheckUpdateAndOpenBottomSheetIfNeed
import ru.vafeen.universityschedule.presentation.components.ui_utils.UpdateProgress
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
            MainTheme {
                val dark = isSystemInDarkTheme()
                val defaultColor = Theme.colors.mainColor
                val settings by viewModel.settings.collectAsState()
                val mainColor by remember {
                    derivedStateOf {
                        settings.getMainColorForThisTheme(isDark = dark) ?: defaultColor
                    }
                }
                val versionCode by remember { mutableLongStateOf(getVersionCode()) }
                var updateIsShowed by remember { mutableStateOf(false) }
                val isUpdateInProcess by viewModel.isUpdateInProcessFlow.collectAsState(false)
                val downloadedPercentage by viewModel.percentageFlow.collectAsState(0f)

                RequestNotificationPermission()

                if (!updateIsShowed) CheckUpdateAndOpenBottomSheetIfNeed(viewModel = viewModel) {
                    updateIsShowed = true
                }
                if (updateIsShowed && settings.lastDemonstratedVersion < versionCode && settings.releaseBody != "") {
                    NewVersionInfoBottomSheet(viewModel = viewModel) {
                        viewModel.saveSettingsToSharedPreferences(
                            settings.copy(lastDemonstratedVersion = versionCode)
                        )
                    }
                }

                val navController = rememberNavController()

                Scaffold(bottomBar = {
                    BottomBar(
                        initialSelectedScreen = viewModel.startScreen,
                        containerColor = mainColor,
                        navController = navController
                    )
                }) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .background(Theme.colors.singleTheme)
                            .padding(innerPadding),
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = viewModel.startScreen.route
                        ) {
                            composable(Screen.Main.route) {
                                MainScreen(
                                    navController = navController,
                                ).Content()
                            }
                            composable(Screen.Settings.route) {
                                SettingsScreen(
                                    navController = navController,
                                ).Content()
                            }
                        }
                        if (isUpdateInProcess) UpdateProgress(percentage = downloadedPercentage)
                    }
                }
            }
        }
    }
}