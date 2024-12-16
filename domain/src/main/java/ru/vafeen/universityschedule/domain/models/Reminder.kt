package ru.vafeen.universityschedule.domain.models

import ru.vafeen.universityschedule.domain.models.model_additions.ReminderType
import ru.vafeen.universityschedule.domain.scheduler.duration.RepeatDuration
import java.time.LocalDateTime

/**
 * Класс, представляющий напоминание.
 *
 * @property id Уникальный идентификатор напоминания.
 * @property idOfReminder Идентификатор напоминания.
 * @property title Заголовок напоминания.
 * @property text Текст напоминания.
 * @property dt Дата и время, когда должно сработать напоминание.
 * @property duration Продолжительность повторения напоминания (по умолчанию - каждую неделю).
 * @property type Тип напоминания (по умолчанию - после начала пары).
 */
data class Reminder(
    val id: Int = 0,
    val idOfReminder: Int,
    val title: String,
    val text: String,
    val dt: LocalDateTime,
    val duration: RepeatDuration = RepeatDuration.EVERY_WEEK,
    val type: ReminderType = ReminderType.AFTER_BEGINNING_LESSON
)
