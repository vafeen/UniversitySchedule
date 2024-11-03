package ru.vafeen.universityschedule.domain.usecase.db

import ru.vafeen.universityschedule.domain.database.DatabaseRepository
import ru.vafeen.universityschedule.domain.models.Reminder
import ru.vafeen.universityschedule.domain.usecase.base.UseCase

class GetReminderByIdOfReminderUseCase(private val repository: DatabaseRepository) : UseCase {
    fun use(idOfReminder: Int): Reminder? =
        repository.getReminderByIdOfReminder(idOfReminder = idOfReminder)
}