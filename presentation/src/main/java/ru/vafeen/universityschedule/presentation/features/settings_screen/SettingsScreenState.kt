package ru.vafeen.universityschedule.presentation.features.settings_screen

import ru.vafeen.universityschedule.domain.GSheetsServiceRequestStatus
import ru.vafeen.universityschedule.domain.models.Settings

data class SettingsScreenState(
    val settings: Settings = Settings(),
    val gSheetsServiceRequestStatus: GSheetsServiceRequestStatus = GSheetsServiceRequestStatus.Waiting,
    val subGroups:List<String> = listOf(),
)
