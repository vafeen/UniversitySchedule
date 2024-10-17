package ru.vafeen.universityschedule.last.utils

import java.time.DayOfWeek


fun DayOfWeek.ruDayOfWeek(ruDaysOfWeek: List<String>): String = ruDaysOfWeek[this.value - 1]
