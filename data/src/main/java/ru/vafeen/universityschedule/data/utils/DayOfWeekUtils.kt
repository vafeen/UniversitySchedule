package ru.vafeen.universityschedule.data.utils

import java.time.DayOfWeek


internal fun DayOfWeek.ruDayOfWeek(ruDaysOfWeek: List<String>): String = ruDaysOfWeek[this.value - 1]
