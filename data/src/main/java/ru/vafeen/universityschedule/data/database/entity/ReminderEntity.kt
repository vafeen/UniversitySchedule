package ru.vafeen.universityschedule.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "Reminder")
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idOfReminder: Int,
    val title: String,
    val text: String,
    val dt: LocalDateTime,
    val duration: String = "EVERY_WEEK",
    val type: String = "AFTER_BEGINNING_LESSON",
)