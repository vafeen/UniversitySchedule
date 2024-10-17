package ru.vafeen.universityschedule.domain.database.usecase

import ru.vafeen.universityschedule.data.database.AppDatabase
import ru.vafeen.universityschedule.data.database.entity.Reminder

internal class UpdateAllRemindersUseCase(private val db: AppDatabase) {
    suspend operator fun invoke(vararg reminder: Reminder) =
        db.reminderDao().update(entity = reminder)
}