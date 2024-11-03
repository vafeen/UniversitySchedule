package ru.vafeen.universityschedule.domain.usecase.db

import ru.vafeen.universityschedule.domain.database.DatabaseRepository
import ru.vafeen.universityschedule.domain.models.Reminder
import ru.vafeen.universityschedule.domain.usecase.base.UseCase

class DeleteRemindersUseCase(private val repository: DatabaseRepository) : UseCase {
    suspend fun use(vararg reminder: Reminder) =
        repository.deleteReminders(reminder.toList())
}