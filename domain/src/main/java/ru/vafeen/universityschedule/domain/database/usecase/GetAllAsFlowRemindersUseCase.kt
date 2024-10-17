package ru.vafeen.universityschedule.domain.database.usecase

import kotlinx.coroutines.flow.Flow
import ru.vafeen.universityschedule.data.database.AppDatabase
import ru.vafeen.universityschedule.data.database.entity.Reminder

class GetAllAsFlowRemindersUseCase(private val db: AppDatabase) {
    operator fun invoke(): Flow<List<Reminder>> = db.reminderDao().getAllAsFlow()
}