package ru.vafeen.universityschedule.presentation.features.settings_screen

import ru.vafeen.universityschedule.domain.GSheetsServiceRequestStatus
import ru.vafeen.universityschedule.domain.models.Settings

sealed class SettingsScreenEvent {
    data object MeowEvent : SettingsScreenEvent()
    data class SaveSettingsEvent(val saving: (Settings) -> Settings) : SettingsScreenEvent()
    data class GSheetsRequestStatusUpdate(val newStatus: GSheetsServiceRequestStatus) :
        SettingsScreenEvent()
}
