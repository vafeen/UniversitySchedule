package ru.vafeen.universityschedule.domain.usecase.scheduler

import android.content.Intent
import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.universityschedule.domain.models.Reminder
import ru.vafeen.universityschedule.domain.scheduler.Scheduler

class CancelJobUseCase {
    private val scheduler: Scheduler by inject(clazz = Scheduler::class.java)
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