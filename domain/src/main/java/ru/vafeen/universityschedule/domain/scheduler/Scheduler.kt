package ru.vafeen.universityschedule.domain.scheduler

import ru.vafeen.universityschedule.domain.models.Reminder

interface Scheduler {
    fun scheduleRepeatingJob(reminder: Reminder)

    fun cancelJob(reminder: Reminder)
}