package ru.vafeen.universityschedule.presentation.components.bottom_bar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.vafeen.universityschedule.presentation.navigation.BottomBarNavigator
import ru.vafeen.universityschedule.presentation.navigation.Screen
import ru.vafeen.universityschedule.presentation.utils.suitableColor
import ru.vafeen.universityschedule.resources.R

/**
 * Компонент нижней панели навигации (Bottom Bar).
 *
 * Этот компонент отображает нижнюю панель навигации с двумя пунктами: "Главная" и "Настройки".
 * Позволяет пользователю переключаться между экранами приложения.
 *
 * @param selectedScreen Текущий выбранный экран [Screen].
 * @param bottomBarNavigator Навигатор для обработки переходов между экранами.
 *                           Может быть null, если навигация не требуется.
 * @param containerColor Цвет фона нижней панели навигации.
 */
@Composable
internal fun BottomBar(
    selectedScreen: Screen,
    bottomBarNavigator: BottomBarNavigator? = null,
    containerColor: Color,
) {
    // Настройка цветов для элементов навигации.
    val colors = NavigationBarItemDefaults.colors(
        unselectedIconColor = containerColor.suitableColor().copy(alpha = 0.5f),
        indicatorColor = containerColor,
        disabledIconColor = containerColor.suitableColor(),
    )

    // Создание нижней панели приложения.
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .height(55.dp),
        containerColor = containerColor
    ) {
        // Элемент навигации для экрана "Главная".
        NavigationBarItem(
            modifier = Modifier.weight(1 / 2f),
            selected = selectedScreen == Screen.Main,
            onClick = { bottomBarNavigator?.navigateTo(Screen.Main) },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = stringResource(R.string.icon_home_screen)
                )
            },
            colors = colors,
            enabled = selectedScreen != Screen.Main // Деактивировать, если выбран текущий экран.
        )

        // Элемент навигации для экрана "Настройки".
        NavigationBarItem(
            modifier = Modifier.weight(1 / 2f),
            selected = selectedScreen == Screen.Settings,
            onClick = { bottomBarNavigator?.navigateTo(Screen.Settings) },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.settings),
                    contentDescription = stringResource(R.string.icon_settings_screen)
                )
            },
            enabled = selectedScreen != Screen.Settings, // Деактивировать, если выбран текущий экран.
            colors = colors
        )
    }
}