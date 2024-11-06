package ru.vafeen.universityschedule.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.vafeen.universityschedule.domain.models.model_additions.ReminderType
import ru.vafeen.universityschedule.domain.scheduler.duration.RepeatDuration
import java.time.LocalDateTime

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