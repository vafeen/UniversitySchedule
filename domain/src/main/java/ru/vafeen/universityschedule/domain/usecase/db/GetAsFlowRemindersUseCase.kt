package ru.vafeen.universityschedule.domain.usecase.db

import kotlinx.coroutines.flow.Flow
import ru.vafeen.universityschedule.domain.database.ReminderRepository
import ru.vafeen.universityschedule.domain.models.Reminder
import ru.vafeen.universityschedule.domain.usecase.base.UseCase

class GetAsFlowRemindersUseCase(private val reminderRepository: ReminderRepository) : UseCase {
    fun use(): Flow<Iterable<Reminder>> = reminderRepository.getAsFlowReminders()
}