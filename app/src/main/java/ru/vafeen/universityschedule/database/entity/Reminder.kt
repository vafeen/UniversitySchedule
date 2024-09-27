package ru.vafeen.universityschedule.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class Reminder(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idOfReminder: Int,
    val title: String,
    val text: String,
    val dt: LocalDateTime,
) {
    override fun toString(): String = "$id $title $text"
}