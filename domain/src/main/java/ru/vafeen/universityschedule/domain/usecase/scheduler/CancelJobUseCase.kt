package ru.vafeen.universityschedule.domain.usecase.scheduler

import ru.vafeen.universityschedule.domain.models.Reminder
import ru.vafeen.universityschedule.domain.scheduler.Scheduler
import ru.vafeen.universityschedule.domain.usecase.base.UseCase

/**
 * UseCase для отмены запланированной задачи.
 *
 * Этот класс отвечает за отмену задачи, связанной с указанным напоминанием.
 *
 * @property scheduler Планировщик, используемый для управления задачами (например, отмены запланированных напоминаний).
 */
class CancelJobUseCase(private val scheduler: Scheduler) : UseCase {

    /**
     * Отменяет запланированную задачу для указанного напоминания.
     *
     * @param reminder Напоминание, для которого нужно отменить задачу.
     */
    fun invoke(reminder: Reminder) {
        scheduler.cancelJob(reminder = reminder) // Отмена задачи в планировщике.
    }
}
