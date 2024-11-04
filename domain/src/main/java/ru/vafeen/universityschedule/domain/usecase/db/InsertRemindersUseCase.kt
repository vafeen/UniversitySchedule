package ru.vafeen.universityschedule.domain.usecase.db

import ru.vafeen.universityschedule.domain.database.ReminderRepository
import ru.vafeen.universityschedule.domain.models.Reminder
import ru.vafeen.universityschedule.domain.usecase.base.UseCase

class InsertRemindersUseCase(private val reminderRepository: ReminderRepository) : UseCase {
    suspend fun use(vararg reminders: Reminder) =
        reminderRepository.insertReminders(reminders.toList())
}