package ru.vafeen.universityschedule.domain.network.service

import kotlinx.coroutines.flow.StateFlow
import ru.vafeen.universityschedule.domain.models.Settings

interface SettingsManager {
    val settingsFlow: StateFlow<Settings>
    fun save(settings: Settings)
}