package ru.vafeen.universityschedule.presentation.navigation

import kotlinx.serialization.Serializable


internal sealed class Screen {
    @Serializable
    data object Main : Screen()

    @Serializable
    data object Settings : Screen()
}