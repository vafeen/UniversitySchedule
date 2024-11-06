package ru.vafeen.universityschedule.presentation.utils

import ru.vafeen.universityschedule.domain.models.Settings
import ru.vafeen.universityschedule.domain.models.model_additions.Frequency

internal fun Frequency.changeFrequencyIfDefinedInSettings(settings: Settings): Frequency {
    return if (settings.isSelectedFrequencyCorrespondsToTheWeekNumbers != false)
        this
    else this.getOpposite()
}