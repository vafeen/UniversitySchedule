package ru.vafeen.universityschedule.domain.usecase.db

import ru.vafeen.universityschedule.domain.database.ReminderRepository
import ru.vafeen.universityschedule.domain.models.Reminder
import ru.vafeen.universityschedule.domain.usecase.base.UseCase

class UpdateRemindersUseCase(private val reminderRepository: ReminderRepository) : UseCase {
    suspend fun invoke(vararg reminder: Reminder) =
        reminderRepository.updateReminders(reminder.toList())
}