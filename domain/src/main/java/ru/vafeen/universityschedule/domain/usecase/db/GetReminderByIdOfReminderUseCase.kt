package ru.vafeen.universityschedule.domain.usecase.db

import ru.vafeen.universityschedule.domain.database.ReminderRepository
import ru.vafeen.universityschedule.domain.models.Reminder
import ru.vafeen.universityschedule.domain.usecase.base.UseCase

class GetReminderByIdOfReminderUseCase(private val reminderRepository: ReminderRepository) : UseCase {
    fun invoke(idOfReminder: Int): Reminder? =
        reminderRepository.getReminderByIdOfReminder(idOfReminder = idOfReminder)
}