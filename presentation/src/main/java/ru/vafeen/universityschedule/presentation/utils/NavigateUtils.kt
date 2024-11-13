package ru.vafeen.universityschedule.presentation.utils

import androidx.navigation.NavController
import ru.vafeen.universityschedule.presentation.navigation.Screen

internal fun NavController.navigateeee(route: Screen) {
    popBackStack()
    navigate(route.route)
}