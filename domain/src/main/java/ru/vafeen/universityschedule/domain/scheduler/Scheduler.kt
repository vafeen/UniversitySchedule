package ru.vafeen.universityschedule.domain.scheduler

import ru.vafeen.universityschedule.domain.models.Reminder

/**
 * Интерфейс для планирования напоминаний.
 */
interface Scheduler {

    fun scheduleOneTimeJob(reminder: Reminder)
    /**
     * Запланировать повторяющуюся задачу на основе указанного напоминания.
     *
     * @param reminder Объект [Reminder], представляющий напоминание, которое нужно запланировать.
     */
    fun scheduleRepeatingJob(reminder: Reminder)

    /**
     * Отменить запланированную задачу на основе указанного напоминания.
     *
     * @param reminder Объект [Reminder], представляющий напоминание, которое нужно отменить.
     */
    fun cancelJob(reminder: Reminder)
    fun cancelAll()
}
