package ru.vafeen.universityschedule.domain.utils

fun Long.bytesToMBytes(): Long = this / (1024 * 1024)

fun Long.roundToOneDecimal(): Float = this.let { (Math.round(it * 10f) / 10f) }

