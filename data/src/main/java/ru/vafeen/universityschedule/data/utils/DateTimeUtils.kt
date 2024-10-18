package ru.vafeen.universityschedule.data.utils

import android.content.Context
import ru.vafeen.universityschedule.data.R
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

operator fun LocalTime.compareTo(localDateTime: LocalDateTime): Int {
    return this.compareTo(LocalTime.of(localDateTime.hour, localDateTime.minute))
}

fun LocalDate.getDateString(): String = "${dayOfMonth}." + if (month.value < 10) "0${month.value}"
else month.value

fun LocalDate.getDateStringWithWeekOfDay(context: Context): String =
    "${context.getString(daysOfWeek[dayOfWeek.value - 1])}, ${dayOfMonth}." + if (month.value < 10) "0${month.value}"
    else month.value


fun LocalTime.getTimeStringAsHMS(): String =
    "${
        if (hour < 10) "0$hour" else "$hour"
    }:${
        if (minute < 10) "0$minute" else "$minute"
    }:${
        if (second < 10) "0$second" else "$second"
    }"

internal fun LocalDate.getLastSeptemberOfThisAcademicYear(): LocalDate {
    return if (this >= LocalDate.of(year, Month.SEPTEMBER, 1))
        LocalDate.of(year, Month.SEPTEMBER, 1)
    else LocalDate.of(year - 1, Month.SEPTEMBER, 1)
}

fun LocalDate.getFrequencyByLocalDate(): ru.vafeen.universityschedule.data.database.lesson_additions.Frequency {
    val lastSeptember = getLastSeptemberOfThisAcademicYear()
    val resultFrequency =
        if (get(ChronoField.ALIGNED_WEEK_OF_YEAR) % 2 == 0) ru.vafeen.universityschedule.data.database.lesson_additions.Frequency.Denominator
        else ru.vafeen.universityschedule.data.database.lesson_additions.Frequency.Numerator
    return if (lastSeptember.dayOfWeek == DayOfWeek.SUNDAY)
        resultFrequency.getOpposite()
    else resultFrequency
}

fun LocalTime.toLessonTime(): String = "${hour}:" + if (minute < 10) "0${minute}" else "$minute"

