package ru.vafeen.universityschedule.ui.components.bottom_bar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.vafeen.universityschedule.utils.suitableColor


@Composable
fun BottomBar(
    containerColor: Color,
    clickToScreen1: () -> Unit = {},
    clickToScreen2: () -> Unit = {},
    selected1: Boolean = false,
    selected2: Boolean = false,
) {
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
            selected = selected1,
            onClick = { clickToScreen1() },
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Icon1"
                )
            },
            colors = colors,
            enabled = !selected1
        )
        NavigationBarItem(
            modifier = Modifier.weight(1 / 2f),
            selected = selected2,
            onClick = { clickToScreen2() },
            icon = {
                Icon(
                    Icons.AutoMirrored.Filled.List,
                    contentDescription = "Icon2"
                )
            },
            enabled = !selected2,
            colors = colors
        )
    }
}