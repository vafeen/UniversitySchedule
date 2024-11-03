package ru.vafeen.universityschedule.domain.scheduler

import android.content.Intent
import ru.vafeen.universityschedule.domain.models.Reminder

interface Scheduler {
    fun scheduleRepeatingJob(
        reminder: Reminder,
        intent: Intent
    )

    fun cancelJob(
        reminder: Reminder,
        intent: Intent
    )
}