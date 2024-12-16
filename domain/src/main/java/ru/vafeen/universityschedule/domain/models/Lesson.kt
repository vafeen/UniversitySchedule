package ru.vafeen.universityschedule.domain.models

import ru.vafeen.universityschedule.domain.models.model_additions.Frequency
import java.time.DayOfWeek
import java.time.LocalTime

/**
 * Класс, представляющий пару.
 *
 * @property id Уникальный идентификатор пары.
 * @property dayOfWeek День недели, в который проходит пара (может быть null).
 * @property name Название пары (может быть null).
 * @property startTime Время начала пары.
 * @property endTime Время окончания пары.
 * @property classroom Номер аудитории, в которой проходит пара (может быть null).
 * @property teacher Имя преподавателя (может быть null).
 * @property subGroup Подгруппа, к которой относится пара (может быть null).
 * @property frequency Частота проведения пары (может быть null).
 * @property idOfReminderBeforeLesson Идентификатор напоминания перед парой (может быть null).
 * @property idOfReminderAfterBeginningLesson Идентификатор напоминания после начала пары (может быть null).
 * @property note Примечание к паре (может быть null).
 */
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
    val note: String? = null
) : Comparable<Lesson> {

    /**
     * Возвращает строковое представление объекта [Lesson].
     *
     * @return Строка с информацией о дне недели, названии, времени начала и окончания,
     * аудитории, преподавателе, подгруппе и частоте.
     */
    override fun toString(): String {
        return "\n dayOfWeek=${dayOfWeek ?: "\"is null\""} name=${name ?: "\"is null\""} st=${startTime}-et=${endTime} classroom=${classroom ?: "\"is null\""} tchr=${teacher ?: "\"is null\""} sbgr=${subGroup ?: "\"is null\""} fr=${frequency ?: "\"is null\""}"
    }

    /**
     * Сравнивает текущий объект [Lesson] с другим объектом [Lesson] по времени начала.
     *
     * @param other Другой объект [Lesson] для сравнения.
     * @return 1, если время начала больше; 0, если равно; -1 в противном случае.
     */
    override fun compareTo(other: Lesson): Int = when {
        startTime > other.startTime -> 1
        startTime == other.startTime -> 0
        else -> -1
    }
}
