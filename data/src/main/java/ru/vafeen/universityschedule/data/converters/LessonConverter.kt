package ru.vafeen.universityschedule.data.converters

import ru.vafeen.universityschedule.data.database.entity.LessonEntity
import ru.vafeen.universityschedule.domain.converter.BaseConverter
import ru.vafeen.universityschedule.domain.models.Lesson
import ru.vafeen.universityschedule.domain.models.model_additions.Frequency

/**
 * Конвертер для преобразования [LessonEntity] в [Lesson] и обратно.
 *
 * Используется для преобразования данных между сущностью базы данных и моделью доменного уровня.
 */
internal class LessonConverter : BaseConverter<LessonEntity, Lesson> {

    /**
     * Преобразует [LessonEntity] в [Lesson].
     *
     * @param a Экземпляр [LessonEntity], полученный из базы данных.
     * @return Экземпляр [Lesson], используемый на уровне доменной модели.
     */
    override fun convertAB(a: LessonEntity): Lesson = Lesson(
        id = a.id,
        dayOfWeek = a.dayOfWeek,
        name = a.name,
        startTime = a.startTime,
        endTime = a.endTime,
        classroom = a.classroom,
        teacher = a.teacher,
        subGroup = a.subGroup,
        frequency = a.frequency?.let { Frequency.valueOf(it) },
        idOfReminderBeforeLesson = a.idOfReminderBeforeLesson,
        idOfReminderAfterBeginningLesson = a.idOfReminderAfterBeginningLesson,
        note = a.note
    )

    /**
     * Преобразует [Lesson] в [LessonEntity].
     *
     * @param b Экземпляр [Lesson], используемый на уровне доменной модели.
     * @return Экземпляр [LessonEntity], предназначенный для сохранения в базу данных.
     */
    override fun convertBA(b: Lesson): LessonEntity = LessonEntity(
        id = b.id,
        dayOfWeek = b.dayOfWeek,
        name = b.name,
        startTime = b.startTime,
        endTime = b.endTime,
        classroom = b.classroom,
        teacher = b.teacher,
        subGroup = b.subGroup,
        frequency = b.frequency?.toString(),
        idOfReminderBeforeLesson = b.idOfReminderBeforeLesson,
        idOfReminderAfterBeginningLesson = b.idOfReminderAfterBeginningLesson,
        note = b.note
    )
}
