package ru.vafeen.universityschedule.domain.usecase.db

import kotlinx.coroutines.flow.Flow
import ru.vafeen.universityschedule.domain.database.DatabaseRepository
import ru.vafeen.universityschedule.domain.models.Reminder
import ru.vafeen.universityschedule.domain.usecase.base.UseCase

class GetAsFlowRemindersUseCase(private val repository: DatabaseRepository) : UseCase {
    fun use(): Flow<Iterable<Reminder>> = repository.getAsFlowReminders()
}