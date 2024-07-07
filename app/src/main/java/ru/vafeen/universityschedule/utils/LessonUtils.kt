package ru.vafeen.universityschedule.utils

import ru.vafeen.universityschedule.database.entity.Lesson
import java.time.LocalTime

fun Lesson.nowIsLesson(datetime: LocalTime): Boolean =
    (datetime.hour * 60 * 60 + datetime.minute * 60 + datetime.second) in
            (startTime.hour * 60 * 60 + startTime.minute * 60)..(endTime.hour * 60 * 60 + endTime.minute * 60)
