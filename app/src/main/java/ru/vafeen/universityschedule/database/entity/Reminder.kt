package ru.vafeen.universityschedule.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.vafeen.universityschedule.database.ReminderType
import java.time.LocalDateTime

@Entity
data class Reminder(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idOfReminder: Int,
    val title: String,
    val text: String,
    val dt: LocalDateTime,
    val type: ReminderType = ReminderType.AFTER_BEGINNING_LESSON,
) {
    override fun toString(): String = "$id|$title|$text|$dt|"
}