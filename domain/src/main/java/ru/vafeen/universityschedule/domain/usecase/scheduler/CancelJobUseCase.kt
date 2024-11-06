package ru.vafeen.universityschedule.domain.usecase.scheduler

import android.content.Intent
import ru.vafeen.universityschedule.domain.models.Reminder
import ru.vafeen.universityschedule.domain.scheduler.Scheduler
import ru.vafeen.universityschedule.domain.usecase.base.UseCase

class CancelJobUseCase(private val scheduler: Scheduler) : UseCase {
    fun use(
        reminder: Reminder,
        intent: Intent
    ) {
        scheduler.cancelJob(
            reminder = reminder,
            intent = intent
        )
    }
}