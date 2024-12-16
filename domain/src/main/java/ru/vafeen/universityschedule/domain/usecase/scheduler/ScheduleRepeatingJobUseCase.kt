package ru.vafeen.universityschedule.domain.usecase.scheduler

import ru.vafeen.universityschedule.domain.models.Reminder
import ru.vafeen.universityschedule.domain.scheduler.Scheduler
import ru.vafeen.universityschedule.domain.usecase.base.UseCase

/**
 * UseCase для планирования повторяющейся задачи.
 *
 * Этот класс отвечает за планирование задачи, связанной с указанным напоминанием.
 *
 * @property scheduler Планировщик, используемый для управления задачами (например, планирования повторяющихся напоминаний).
 */
class ScheduleRepeatingJobUseCase(private val scheduler: Scheduler) : UseCase {

    /**
     * Планирует повторяющуюся задачу для указанного напоминания.
     *
     * @param reminder Напоминание, для которого нужно запланировать повторяющуюся задачу.
     */
    fun invoke(reminder: Reminder) {
        scheduler.scheduleRepeatingJob(reminder = reminder) // Планирование задачи в планировщике.
    }
}
