package ru.vafeen.universityschedule.domain.usecase.db

import ru.vafeen.universityschedule.domain.database.DatabaseRepository
import ru.vafeen.universityschedule.domain.models.Reminder
import ru.vafeen.universityschedule.domain.usecase.base.UseCase

class InsertRemindersUseCase(private val repository: DatabaseRepository) : UseCase {
    suspend fun use(vararg reminders: Reminder) =
        repository.insertReminders(reminders.toList())
}