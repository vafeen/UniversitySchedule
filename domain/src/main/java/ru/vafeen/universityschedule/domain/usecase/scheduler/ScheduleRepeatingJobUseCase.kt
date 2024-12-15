package ru.vafeen.universityschedule.domain.usecase.scheduler

import ru.vafeen.universityschedule.domain.models.Reminder
import ru.vafeen.universityschedule.domain.scheduler.Scheduler
import ru.vafeen.universityschedule.domain.usecase.base.UseCase

class ScheduleRepeatingJobUseCase(private val scheduler: Scheduler) : UseCase {
    fun invoke(reminder: Reminder) {
        scheduler.scheduleRepeatingJob(reminder = reminder)
    }
}