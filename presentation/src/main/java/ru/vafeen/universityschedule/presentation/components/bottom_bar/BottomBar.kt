package ru.vafeen.universityschedule.presentation.components.bottom_bar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.vafeen.universityschedule.presentation.navigation.Screen
import ru.vafeen.universityschedule.presentation.utils.navigateeee
import ru.vafeen.universityschedule.presentation.utils.suitableColor
import ru.vafeen.universityschedule.resources.R


@Composable
internal fun BottomBar(
    initialSelectedScreen: Screen,
    containerColor: Color,
    navController: NavController?,
) {
    var selectedScreen by remember { mutableStateOf(initialSelectedScreen) }
    val colors = NavigationBarItemDefaults.colors(
        unselectedIconColor = containerColor.suitableColor().copy(alpha = 0.5f),
        indicatorColor = containerColor,
        disabledIconColor = containerColor.suitableColor(),
    )
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .height(55.dp),
        containerColor = containerColor
    ) {
        NavigationBarItem(
            modifier = Modifier.weight(1 / 2f),
            selected = selectedScreen == Screen.Main,
            onClick = {
                if (selectedScreen != Screen.Main) {
                    navController?.navigateeee(Screen.Main)
                    selectedScreen = Screen.Main
                }
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = "Home"
                )
            },
            colors = colors,
            enabled = selectedScreen != Screen.Main
        )
        NavigationBarItem(
            modifier = Modifier.weight(1 / 2f),
            selected = selectedScreen == Screen.Settings,
            onClick = {
                if (selectedScreen != Screen.Settings) {
                    navController?.navigateeee(Screen.Settings)
                    selectedScreen = Screen.Settings
                }
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.settings),
                    contentDescription = "Settings"
                )
            },
            enabled = selectedScreen != Screen.Settings,
            colors = colors
        )
    }
}