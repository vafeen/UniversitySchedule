package ru.vafeen.universityschedule.domain.usecase.scheduler

import kotlinx.coroutines.flow.first
import ru.vafeen.universityschedule.domain.scheduler.Scheduler
import ru.vafeen.universityschedule.domain.usecase.db.GetAsFlowRemindersUseCase

class RebootingRemindersUseCase(
    private val scheduler: Scheduler,
    private val getAsFlowRemindersUseCase: GetAsFlowRemindersUseCase
) {
    suspend fun invoke() {
        getAsFlowRemindersUseCase.invoke().first().forEach {
            scheduler.cancelJob(it)
            scheduler.scheduleRepeatingJob(it)
        }
    }
}