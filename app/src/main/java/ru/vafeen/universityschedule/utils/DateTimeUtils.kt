package ru.vafeen.universityschedule.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

operator fun LocalTime.compareTo(localDateTime: LocalDateTime): Int {
    return this.compareTo(LocalTime.of(localDateTime.hour, localDateTime.minute))
}

fun LocalDate.getDateString(): String = "${dayOfMonth}." + if (month.value < 10) "0${month.value}"
else month.value


fun LocalTime.getTimeStringAsHMS(): String =
    if (hour < 10) "0$hour" else "$hour:" +
            if (minute < 10) "0$minute" else "$minute:" +
                    if (second < 10) "0$second" else "$second"

