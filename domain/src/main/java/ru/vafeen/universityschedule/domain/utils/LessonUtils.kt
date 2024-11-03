package ru.vafeen.universityschedule.domain.utils

import ru.vafeen.universityschedule.domain.models.Lesson

internal fun Iterable<Lesson>.containsLesson(lesson: Lesson): Lesson? = filter {
    it.dayOfWeek == lesson.dayOfWeek &&
            it.name == lesson.name &&
            it.startTime == lesson.startTime &&
            it.endTime == lesson.endTime &&
            it.classroom == lesson.classroom &&
            it.teacher == lesson.teacher &&
            it.subGroup == lesson.subGroup &&
            it.frequency == lesson.frequency
}.let {
    if (it.isEmpty()) null
    else it[0]
}