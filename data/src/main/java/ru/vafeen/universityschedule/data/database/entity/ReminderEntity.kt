package ru.vafeen.universityschedule.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.vafeen.universityschedule.domain.models.model_additions.ReminderType
import ru.vafeen.universityschedule.domain.scheduler.duration.RepeatDuration
import java.time.LocalDateTime

/**
 * Сущность для представления напоминания в базе данных.
 *
 * @property id Уникальный идентификатор напоминания (генерируется автоматически).
 * @property idOfReminder Идентификатор напоминания.
 * @property title Заголовок напоминания.
 * @property text Текст напоминания.
 * @property dt Дата и время, когда должно сработать напоминание.
 * @property duration Периодичность повторения напоминания (по умолчанию - каждую неделю).
 * @property type Тип напоминания (по умолчанию - перед парой).
 */
@Entity(tableName = "Reminder")
internal data class ReminderEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idOfReminder: Int,
    val title: String,
    val text: String,
    val dt: LocalDateTime,
    val duration: RepeatDuration = RepeatDuration.EVERY_WEEK,
    val type: ReminderType = ReminderType.BEFORE_LESSON
)
