package ru.vafeen.universityschedule.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.UUID

@Entity
data class Reminder(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val UUID: UUID,
    val title: String,
    val text: String,
    val dt: LocalDateTime,
) {
    override fun toString(): String = "$id $title $text"
}