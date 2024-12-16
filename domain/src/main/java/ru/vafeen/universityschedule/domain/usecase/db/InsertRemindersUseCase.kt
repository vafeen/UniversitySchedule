package ru.vafeen.universityschedule.domain.usecase.db

import ru.vafeen.universityschedule.domain.database.ReminderRepository
import ru.vafeen.universityschedule.domain.models.Reminder
import ru.vafeen.universityschedule.domain.usecase.base.UseCase

/**
 * UseCase для добавления напоминаний в базу данных.
 *
 * Этот класс отвечает за вставку одного или нескольких напоминаний в репозиторий.
 *
 * @property reminderRepository Репозиторий, используемый для взаимодействия с данными напоминаний.
 */
class InsertRemindersUseCase(private val reminderRepository: ReminderRepository) : UseCase {

    /**
     * Вставляет указанные напоминания в базу данных.
     *
     * @param reminders Напоминания, которые нужно добавить. Можно передавать несколько напоминаний в виде vararg.
     */
    suspend fun invoke(vararg reminders: Reminder) =
        reminderRepository.insertReminders(reminders.toList())
}