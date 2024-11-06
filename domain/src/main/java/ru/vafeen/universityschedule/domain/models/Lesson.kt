package ru.vafeen.universityschedule.domain.models

import ru.vafeen.universityschedule.domain.models.model_additions.Frequency
import java.time.DayOfWeek
import java.time.LocalTime

data class Lesson(
    val id: Int = 0,
    val dayOfWeek: DayOfWeek? = null,
    val name: String? = null,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val classroom: String? = null,
    val teacher: String? = null,
    val subGroup: String? = null,
    val frequency: Frequency? = null,
    val idOfReminderBeforeLesson: Int? = null,
    val idOfReminderAfterBeginningLesson: Int? = null,
) : Comparable<Lesson> {
    override fun toString(): String {
        return "\n dayOfWeek=${dayOfWeek ?: "\"is null\""} name=${name ?: "\"is null\""} st=${startTime}-et=${endTime} classrom=${classroom ?: "\"is null\""} tchr=${teacher ?: "\"is null\""} sbgr=${subGroup ?: "\"is null\""} fr=${frequency ?: "\"is null\""}"
    }

    override fun compareTo(other: Lesson): Int = when {
        startTime > other.startTime -> 1
        startTime == other.startTime -> 0
        else -> -1
    }
}