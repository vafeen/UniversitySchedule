package ru.vafeen.universityschedule.domain.converters

import ru.vafeen.universityschedule.data.database.entity.LessonEntity
import ru.vafeen.universityschedule.domain.converters.base.BaseConverter
import ru.vafeen.universityschedule.domain.database.models.Lesson
import ru.vafeen.universityschedule.domain.model_additions.Frequency

internal class LessonConverter : BaseConverter<LessonEntity, Lesson> {

    override fun convertEntityDTO(e: LessonEntity): Lesson = Lesson(
        id = e.id,
        dayOfWeek = e.dayOfWeek,
        name = e.name,
        startTime = e.startTime,
        endTime = e.endTime,
        classroom = e.classroom,
        teacher = e.teacher,
        subGroup = e.subGroup,
        frequency = e.frequency?.let { Frequency.valueOf(it) },
        idOfReminderBeforeLesson = e.idOfReminderBeforeLesson,
        idOfReminderAfterBeginningLesson = e.idOfReminderAfterBeginningLesson
    )


    override fun convertDTOEntity(d: Lesson): LessonEntity = LessonEntity(
        id = d.id,
        dayOfWeek = d.dayOfWeek,
        name = d.name,
        startTime = d.startTime,
        endTime = d.endTime,
        classroom = d.classroom,
        teacher = d.teacher,
        subGroup = d.subGroup,
        frequency = d.frequency?.toString(),
        idOfReminderBeforeLesson = d.idOfReminderBeforeLesson,
        idOfReminderAfterBeginningLesson = d.idOfReminderAfterBeginningLesson
    )

}