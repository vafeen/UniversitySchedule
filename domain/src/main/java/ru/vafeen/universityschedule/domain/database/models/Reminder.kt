package ru.vafeen.universityschedule.domain.database.models

import ru.vafeen.universityschedule.domain.model_additions.ReminderType
import ru.vafeen.universityschedule.domain.planner.duration.RepeatDuration
import java.time.LocalDateTime

data class Reminder(
    val id: Int = 0,
    val idOfReminder: Int,
    val title: String,
    val text: String,
    val dt: LocalDateTime,
    val duration: RepeatDuration = RepeatDuration.EVERY_WEEK,
    val type: ReminderType = ReminderType.AFTER_BEGINNING_LESSON,
)
