package ru.vafeen.universityschedule.domain.network.service

import kotlinx.coroutines.flow.StateFlow
import ru.vafeen.universityschedule.domain.models.Settings

/**
 * Интерфейс для управления настройками приложения.
 */
interface SettingsManager {

    /**
     * Поток состояний, представляющий текущие настройки приложения.
     */
    val settingsFlow: StateFlow<Settings>

    /**
     * Сохраняет изменения в настройках.
     *
     * @param saving Функция, принимающая текущие настройки и возвращающая измененные настройки.
     */
    fun save(saving: (Settings) -> Settings)
}
