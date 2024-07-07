package ru.vafeen.universityschedule.ui.components.bottom_bar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.vafeen.universityschedule.ui.theme.ScheduleTheme


@Composable
fun BottomBar(
    clickToScreen1: () -> Unit = {},
    clickToScreen2: () -> Unit = {},
    selected1: Boolean = false,
    selected2: Boolean = false,
) {
    val colors = NavigationBarItemDefaults.colors(
        unselectedIconColor = Color.Gray,
        indicatorColor = ScheduleTheme.colors.mainColor,
        disabledIconColor = Color.Black
    )
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        containerColor = ScheduleTheme.colors.mainColor
    ) {
        NavigationBarItem(
            modifier = Modifier.weight(1 / 2f),
            selected = selected1,
            onClick = { clickToScreen1() },
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Icon1",
//                    tint =
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
                    contentDescription = "Icon2",
//                    tint =
                )
            },
            enabled = !selected2,
            colors = colors
        )
    }
}