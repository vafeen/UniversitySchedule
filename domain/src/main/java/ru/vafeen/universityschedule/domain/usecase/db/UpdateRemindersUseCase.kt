package ru.vafeen.universityschedule.domain.usecase.db

import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.universityschedule.data.database.DatabaseRepository
import ru.vafeen.universityschedule.domain.converters.ReminderConverter
import ru.vafeen.universityschedule.domain.database.models.Reminder

class UpdateRemindersUseCase {
    private val repository: DatabaseRepository by inject(clazz = DatabaseRepository::class.java)
    private val reminderConverter: ReminderConverter by inject(clazz = ReminderConverter::class.java)

    suspend operator fun invoke(vararg reminder: Reminder) =
        repository.updateReminders(reminderConverter.convertDTOEntityList(reminder.toList()))
}