package ru.vafeen.universityschedule.domain.usecase.db

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.universityschedule.domain.converters.ReminderConverter
import ru.vafeen.universityschedule.domain.database.DatabaseRepository
import ru.vafeen.universityschedule.domain.models.Reminder

class GetAsFlowRemindersUseCase {
    private val repository: DatabaseRepository by inject(clazz = DatabaseRepository::class.java)
    private val reminderConverter: ReminderConverter by inject(clazz = ReminderConverter::class.java)

    fun use(): Flow<Iterable<Reminder>> =
        repository.getAsFlowReminders().map { reminderEntities ->
            reminderConverter.convertABList(reminderEntities)
        }
}