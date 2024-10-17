package ru.vafeen.universityschedule.last.utils

import ru.vafeen.universityschedule.last.noui.lesson_additions.Frequency
import ru.vafeen.universityschedule.last.ui.components.Settings

fun Frequency.changeFrequencyIfDefinedInSettings(settings: Settings): Frequency {
    return if (settings.isSelectedFrequencyCorrespondsToTheWeekNumbers != false)
        this
    else this.getOpposite()
}