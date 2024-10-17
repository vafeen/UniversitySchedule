package ru.vafeen.universityschedule.domain.utils

import ru.vafeen.universityschedule.data.database.lesson_additions.Frequency
import ru.vafeen.universityschedule.domain.Settings

fun Frequency.changeFrequencyIfDefinedInSettings(settings: Settings): Frequency {
    return if (settings.isSelectedFrequencyCorrespondsToTheWeekNumbers != false)
        this
    else this.getOpposite()
}