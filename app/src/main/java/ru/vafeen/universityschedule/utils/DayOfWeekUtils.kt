package ru.vafeen.universityschedule.utils

import java.time.DayOfWeek


fun DayOfWeek.ruDayOfWeek(ruDaysOfWeek: List<String>): String = ruDaysOfWeek[this.value - 1]
