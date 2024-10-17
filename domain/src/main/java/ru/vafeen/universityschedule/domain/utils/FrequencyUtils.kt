package ru.vafeen.universityschedule.domain.utils

fun ru.vafeen.universityschedule.data.database.lesson_additions.Frequency.changeFrequencyIfDefinedInSettings(settings: ru.vafeen.universityschedule.domain.Settings): ru.vafeen.universityschedule.data.database.lesson_additions.Frequency {
    return if (settings.isSelectedFrequencyCorrespondsToTheWeekNumbers != false)
        this
    else this.getOpposite()
}