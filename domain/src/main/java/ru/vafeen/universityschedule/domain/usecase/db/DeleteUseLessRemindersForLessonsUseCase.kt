package ru.vafeen.universityschedule.domain.usecase.db

import kotlinx.coroutines.flow.first
import ru.vafeen.universityschedule.domain.models.Lesson
import ru.vafeen.universityschedule.domain.models.Reminder
import ru.vafeen.universityschedule.domain.scheduler.Scheduler
import ru.vafeen.universityschedule.domain.usecase.base.UseCase

/**
 * UseCase для удаления напоминаний, связанных с парами.
 * @property deleteRemindersUseCase UseCase для удаления напоминаний.
 * @property getAsFlowRemindersUseCase UseCase для получения списка напоминаний в виде Flow.
 * @property scheduler Планировщик, используемый для управления задачами (например, отмены запланированных напоминаний).
 */
class DeleteUseLessRemindersForLessonsUseCase(
    private val deleteRemindersUseCase: DeleteRemindersUseCase,
    private val getAsFlowRemindersUseCase: GetAsFlowRemindersUseCase,
    private val getAsFlowLessonsUseCase: GetAsFlowLessonsUseCase,
    private val scheduler: Scheduler,
) : UseCase {

    /**
     * Удаляет напоминания для заданных пар.
     * Также удаляет напоминания, которые не связаны ни с одной парой.
     * 1. Получает список всех существующих напоминаний из базы данных.
     * 2. Для каждой пары проверяет наличие напоминаний "до пары" и "после начала пары".
     * 3. Если напоминание найдено, оно отменяется в планировщике и добавляется в список для удаления.
     * 4. Удаляет напоминания, которые не связаны ни с одной парой.
     * 5. Все собранные напоминания удаляются с помощью `deleteRemindersUseCase`.
     *
     * @param lessonsForDelete Список пар, для которых нужно удалить напоминания.
     */
    suspend fun invoke(lessonsForDelete: List<Lesson>) {
        // Получаем все напоминания в виде списка, блокируя поток до получения первого значения.
        val reminders = getAsFlowRemindersUseCase.invoke().first()
        val remindersForDelete = mutableListOf<Reminder>()
        val lessons = getAsFlowLessonsUseCase.invoke().first()
        // Обрабатываем каждую пару из списка на удаление.
        lessonsForDelete.forEach {
            // Проверяем и удаляем напоминания "до пары".
            if (reminders.any { r ->
                    r.idOfReminder == it.idOfReminderBeforeLesson
                }) {
                val reminder = reminders.first { r ->
                    r.idOfReminder == it.idOfReminderBeforeLesson
                }
                scheduler.cancelJob(reminder) // Отмена задачи в планировщике.
                remindersForDelete.add(reminder) // Добавляем напоминание в список для удаления.
            }
            // Проверяем и удаляем напоминания "после начала пары".
            if (reminders.any { r ->
                    r.idOfReminder == it.idOfReminderAfterBeginningLesson
                }) {
                val reminder = reminders.first { r ->
                    r.idOfReminder == it.idOfReminderAfterBeginningLesson
                }
                scheduler.cancelJob(reminder) // Отмена задачи в планировщике.
                remindersForDelete.add(reminder) // Добавляем напоминание в список для удаления.
            }
        }

        // Удаляем напоминания, которые не связаны с парами.
        reminders.forEach { reminder ->
            // Есть ли в бд пара связанная с текущим напоминанием
            lessons.any { lesson ->
                lesson.idOfReminderBeforeLesson == reminder.idOfReminder ||
                        lesson.idOfReminderAfterBeginningLesson == reminder.idOfReminder
            }.let {
                if (!it) {
                    scheduler.cancelJob(reminder) // Отмена задачи в планировщике.
                    remindersForDelete.add(reminder) // Добавляем напоминание в список для удаления.
                }
            }
        }

        // Удаляем собранные напоминания.
        deleteRemindersUseCase.invoke(*remindersForDelete.toTypedArray())
    }
}