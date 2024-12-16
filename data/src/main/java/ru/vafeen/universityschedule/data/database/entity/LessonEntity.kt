package ru.vafeen.universityschedule.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.DayOfWeek
import java.time.LocalTime

/**
 * Сущность для представления пары в базе данных.
 *
 * @property id Уникальный идентификатор пары (генерируется автоматически).
 * @property dayOfWeek День недели, в который проходит пара.
 * @property name Название пары.
 * @property startTime Время начала пары.
 * @property endTime Время окончания пары.
 * @property classroom Аудитория, в которой проходит пара.
 * @property teacher Имя преподавателя, проводящего пару.
 * @property subGroup Подгруппа, для которой предназначена пара.
 * @property frequency Частота проведения пары.
 * @property idOfReminderBeforeLesson Идентификатор напоминания перед парой.
 * @property idOfReminderAfterBeginningLesson Идентификатор напоминания после начала пары.
 * @property note Заметки пользователя к паре.
 */
@Entity(tableName = "Lesson")
internal data class LessonEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dayOfWeek: DayOfWeek? = null,
    val name: String? = null,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val classroom: String? = null,
    val teacher: String? = null,
    val subGroup: String? = null,
    val frequency: String? = null,
    val idOfReminderBeforeLesson: Int? = null,
    val idOfReminderAfterBeginningLesson: Int? = null,
    val note: String? = null,
) : Comparable<LessonEntity> {

    /**
     * Переопределение метода toString для удобного отображения информации о паре.
     */
    override fun toString(): String {
        return "\n dayOfWeek=${dayOfWeek ?: "\"is null\""} name=${name ?: "\"is null\""} st=${startTime}-et=${endTime} classroom=${classroom ?: "\"is null\""} teacher=${teacher ?: "\"is null\""} subGroup=${subGroup ?: "\"is null\""} frequency=${frequency ?: "\"is null\""}"
    }

    /**
     * Сравнение двух пар по времени начала.
     *
     * @param other Другая пара для сравнения.
     * @return 1, если текущая пара начинается позже, 0, если в одно и то же время, -1, если раньше.
     */
    override fun compareTo(other: LessonEntity): Int = when {
        startTime > other.startTime -> 1
        startTime == other.startTime -> 0
        else -> -1
    }
}