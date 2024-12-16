package ru.vafeen.universityschedule.domain.usecase.db

import ru.vafeen.universityschedule.domain.database.ReminderRepository
import ru.vafeen.universityschedule.domain.models.Reminder
import ru.vafeen.universityschedule.domain.usecase.base.UseCase

/**
 * UseCase для получения напоминания по его идентификатору.
 *
 * Этот класс предоставляет возможность получать конкретное напоминание из репозитория
 * по его идентификатору.
 *
 * @property reminderRepository Репозиторий, используемый для взаимодействия с данными напоминаний.
 */
class GetReminderByIdOfReminderUseCase(private val reminderRepository: ReminderRepository) : UseCase {

    /**
     * Возвращает напоминание по указанному идентификатору.
     *
     * @param idOfReminder Идентификатор напоминания, которое нужно получить.
     * @return Напоминание [Reminder] с указанным идентификатором или null, если напоминание не найдено.
     */
    fun invoke(idOfReminder: Int): Reminder? =
        reminderRepository.getReminderByIdOfReminder(idOfReminder = idOfReminder)
}
