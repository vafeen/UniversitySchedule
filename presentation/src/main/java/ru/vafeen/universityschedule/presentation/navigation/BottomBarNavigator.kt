package ru.vafeen.universityschedule.presentation.navigation

import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.StateFlow

internal interface BottomBarNavigator {
    var navController: NavHostController?
    val currentScreen: StateFlow<Screen>
    fun back()
    fun navigateTo(screen: Screen)
}
