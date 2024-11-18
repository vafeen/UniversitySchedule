package ru.vafeen.universityschedule.presentation.navigation

import kotlinx.coroutines.flow.StateFlow

internal interface BottomBarNavigator {
    val currentScreen: StateFlow<Screen>
    fun back()
    fun navigateTo(screen: Screen)
}