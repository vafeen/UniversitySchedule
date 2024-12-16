package ru.vafeen.universityschedule.domain.usecase.db

import ru.vafeen.universityschedule.domain.database.ReminderRepository
import ru.vafeen.universityschedule.domain.models.Reminder
import ru.vafeen.universityschedule.domain.usecase.base.UseCase

/**
 * UseCase для обновления напоминаний в базе данных.
 *
 * Этот класс отвечает за обновление одного или нескольких напоминаний в репозитории.
 *
 * @property reminderRepository Репозиторий, используемый для взаимодействия с данными напоминаний.
 */
class UpdateRemindersUseCase(private val reminderRepository: ReminderRepository) : UseCase {

    /**
     * Обновляет указанные напоминания в базе данных.
     *
     * @param reminder Напоминания, которые нужно обновить. Можно передавать несколько напоминаний в виде vararg.
     */
    suspend fun invoke(vararg reminder: Reminder) =
        reminderRepository.updateReminders(reminder.toList())
}
