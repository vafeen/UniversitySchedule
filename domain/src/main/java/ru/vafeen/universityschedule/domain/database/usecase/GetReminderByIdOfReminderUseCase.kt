package ru.vafeen.universityschedule.domain.database.usecase

import ru.vafeen.universityschedule.data.database.AppDatabase
import ru.vafeen.universityschedule.data.database.entity.Reminder

internal class GetReminderByIdOfReminderUseCase(private val db: AppDatabase) {
    operator fun invoke(idOfReminder: Int): Reminder? =
        db.reminderDao().getReminderByIdOfReminder(idOfReminder = idOfReminder)
}