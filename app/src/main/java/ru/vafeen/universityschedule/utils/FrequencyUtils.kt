package ru.vafeen.universityschedule.utils

import ru.vafeen.universityschedule.noui.lesson_additions.Frequency
import java.time.LocalDate
import java.time.temporal.ChronoField

fun LocalDate.getFrequencyByLocalDate(): Frequency =
    if (get(ChronoField.ALIGNED_WEEK_OF_YEAR) % 2 == 0) Frequency.Denominator
    else Frequency.Numerator
