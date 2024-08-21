package ru.vafeen.universityschedule.utils

import ru.vafeen.universityschedule.database.entity.Lesson
import ru.vafeen.universityschedule.network.parcelable.ResponseWrapper
import ru.vafeen.universityschedule.network.parcelable.Row

fun Row.toLesson(): Lesson? = this.cells.map {
    it?.value
}.let {
    try {
        Lesson(
            dayOfWeek = it[0]?.toDayOfWeek(),
            name = it[1],
            startTime = "${it[2]}".toTimeOfLessonAsLocalTime(),
            endTime = "${it[3]}".toTimeOfLessonAsLocalTime(),
            classroom = it[4],
            teacher = it[5],
            subGroup = it[6]?.normalizeCase(),
            frequency = it[7]?.toFrequency()
        )
    } catch (e: Exception) {
        null
    }
}

fun ResponseWrapper.toLessonsList(): List<Lesson> {
    val result = mutableListOf<Lesson>()
    for (row in this.table.rows) {
        row.toLesson()?.let { result.add(element = it) }
    }
    return result
}





