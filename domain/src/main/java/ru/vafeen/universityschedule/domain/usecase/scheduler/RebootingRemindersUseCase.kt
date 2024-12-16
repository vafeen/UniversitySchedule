package ru.vafeen.universityschedule.domain.usecase.scheduler

import kotlinx.coroutines.flow.first
import ru.vafeen.universityschedule.domain.scheduler.Scheduler
import ru.vafeen.universityschedule.domain.usecase.db.GetAsFlowRemindersUseCase

/**
 * Use-case для перезапуска всех запланированных задач напоминаний.
 *
 * Этот класс используется для отмены и повторного планирования всех напоминаний,
 * что может быть полезно, например, при перезагрузке устройства или приложения.
 *
 * @property scheduler [Scheduler] интерфейс для управления задачами напоминаний (отмена и планирование).
 * @property getAsFlowRemindersUseCase [GetAsFlowRemindersUseCase] используется для получения списка всех напоминаний в виде потока.
 */
class RebootingRemindersUseCase(
    private val scheduler: Scheduler,
    private val getAsFlowRemindersUseCase: GetAsFlowRemindersUseCase
) {
    /**
     * Перезапускает все существующие задачи напоминаний.
     *
     * - Получает список всех напоминаний с помощью [getAsFlowRemindersUseCase].
     * - Для каждого напоминания отменяет текущую задачу через [Scheduler.cancelJob].
     * - Планирует новую задачу с повторением через [Scheduler.scheduleRepeatingJob].
     *
     * Метод выполняется асинхронно и ожидает завершения работы с напоминаниями.
     */
    suspend fun invoke() {
        getAsFlowRemindersUseCase.invoke()
            .first() // Получаем первый элемент из потока (список напоминаний)
            .forEach {
                scheduler.cancelJob(it) // Отменяем текущую задачу
                scheduler.scheduleRepeatingJob(it) // Планируем новую задачу
            }
    }
}