package ru.vafeen.universityschedule.utils

import ru.vafeen.universityschedule.noui.lesson_additions.Frequency
import ru.vafeen.universityschedule.ui.components.Settings

fun Frequency.changeFrequencyIfDefinedInSettings(settings: Settings): Frequency {
    return if (settings.isSelectedFrequencyCorrespondsToTheWeekNumbers != false)
        this
    else this.getOpposite()
}