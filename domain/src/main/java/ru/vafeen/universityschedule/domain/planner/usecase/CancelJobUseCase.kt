package ru.vafeen.universityschedule.domain.planner.usecase

import android.content.Intent
import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.universityschedule.domain.database.models.Reminder
import ru.vafeen.universityschedule.domain.planner.Scheduler

class CancelJobUseCase {
    private val scheduler: Scheduler by inject(clazz = Scheduler::class.java)
    operator fun invoke(
        reminder: Reminder,
        intent: Intent
    ) {
        scheduler.cancelJob(
            reminder = reminder,
            intent = intent
        )
    }
}