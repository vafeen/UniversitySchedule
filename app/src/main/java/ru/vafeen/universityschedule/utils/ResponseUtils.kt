package ru.vafeen.universityschedule.utils

import android.util.Log
import ru.vafeen.universityschedule.database.entity.Lesson
import ru.vafeen.universityschedule.network.parcelable.Cell
import ru.vafeen.universityschedule.network.parcelable.ResponseWrapper
import ru.vafeen.universityschedule.network.parcelable.Row

fun Row.toLesson(): Lesson? = this.cells.toNonNullListString().let {
    Log.d("fix", "row = $it")
    if (it.size > 8) Lesson(
        dayOfWeek = it[0].toDayOfWeek(),
        name = it[1],
        startTime = it[2].toTimeOfLessonAsLocalTime(),
        endTime = it[3].toTimeOfLessonAsLocalTime(),
        classroom = it[4],
        teacher = it[5],
        subGroup = it[6].toFloat().toInt(),
        frequency = it[7].toFrequency()
    )
    else null
}

fun List<Cell?>.toNonNullListString(): List<String> {
    val result = mutableListOf<String>()
    for (cell in this) cell?.let { result.add(it.value) }
    return result
}

fun ResponseWrapper.toLessonsList(): List<Lesson> {
    val lessons = this.table.rows
    val result = mutableListOf<Lesson>()
    for (row in lessons) {
        row.toLesson()?.let { result.add(element = it) }
    }
    return result
}





