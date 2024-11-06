package ru.vafeen.universityschedule.domain.models

import ru.vafeen.universityschedule.domain.models.model_additions.ReminderType
import ru.vafeen.universityschedule.domain.scheduler.duration.RepeatDuration
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
