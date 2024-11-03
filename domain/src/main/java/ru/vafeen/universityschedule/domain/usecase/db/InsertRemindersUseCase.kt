package ru.vafeen.universityschedule.domain.usecase.db

import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.universityschedule.domain.converters.ReminderConverter
import ru.vafeen.universityschedule.domain.database.DatabaseRepository
import ru.vafeen.universityschedule.domain.models.Reminder

class InsertRemindersUseCase {
    private val repository: DatabaseRepository by inject(clazz = DatabaseRepository::class.java)
    private val reminderConverter: ReminderConverter by inject(clazz = ReminderConverter::class.java)

    suspend fun use(vararg reminders: Reminder) =
        repository.insertReminders(reminderConverter.convertBAList(reminders.toList()))
}