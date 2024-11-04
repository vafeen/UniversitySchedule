package ru.vafeen.universityschedule.domain.usecase.db

import ru.vafeen.universityschedule.domain.database.ReminderRepository
import ru.vafeen.universityschedule.domain.models.Reminder
import ru.vafeen.universityschedule.domain.usecase.base.UseCase

class DeleteRemindersUseCase(private val reminderRepository: ReminderRepository) : UseCase {
    suspend fun use(vararg reminder: Reminder) =
        reminderRepository.deleteReminders(reminder.toList())
}