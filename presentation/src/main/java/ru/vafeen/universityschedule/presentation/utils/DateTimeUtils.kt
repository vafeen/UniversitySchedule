package ru.vafeen.universityschedule.presentation.utils

import android.content.Context
import ru.vafeen.universityschedule.domain.models.model_additions.Frequency
import ru.vafeen.universityschedule.presentation.R
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Month
import java.time.temporal.ChronoField

private val daysOfWeek = listOf(
    R.string.monday,
    R.string.tuesday,
    R.string.wednesday,
    R.string.thursday,
    R.string.friday,
    R.string.satudray,
    R.string.sunday
)

internal operator fun LocalTime.compareTo(localDateTime: LocalDateTime): Int {
    return this.compareTo(LocalTime.of(localDateTime.hour, localDateTime.minute))
}

internal fun LocalDate.getDateStringWithWeekOfDay(context: Context): String =
    "${context.getString(daysOfWeek[dayOfWeek.value - 1])}, ${dayOfMonth}." + if (month.value < 10) "0${month.value}"
    else month.value

internal fun LocalDate.getLastSeptemberOfThisAcademicYear(): LocalDate {
    return if (this >= LocalDate.of(year, Month.SEPTEMBER, 1))
        LocalDate.of(year, Month.SEPTEMBER, 1)
    else LocalDate.of(year - 1, Month.SEPTEMBER, 1)
}

fun LocalDate.getFrequencyByLocalDate(): Frequency {
    val lastSeptember = getLastSeptemberOfThisAcademicYear()
    val resultFrequency =
        if (get(ChronoField.ALIGNED_WEEK_OF_YEAR) % 2 == 0) Frequency.Denominator
        else Frequency.Numerator
    return if (lastSeptember.dayOfWeek == DayOfWeek.SUNDAY)
        resultFrequency.getOpposite()
    else resultFrequency
}

fun LocalTime.toLessonTime(): String = "${hour}:" + if (minute < 10) "0${minute}" else "$minute"

