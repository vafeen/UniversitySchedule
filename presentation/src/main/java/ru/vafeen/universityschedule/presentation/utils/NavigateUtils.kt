package ru.vafeen.universityschedule.presentation.utils

import androidx.navigation.NavController
import ru.vafeen.universityschedule.presentation.navigation.Screen

internal fun NavController.navigateOnScreenWithSingleScreenRule(route: Screen) {
    popBackStack()
    navigate(route.route)
}