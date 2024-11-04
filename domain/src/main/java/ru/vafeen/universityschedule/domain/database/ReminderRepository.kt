package ru.vafeen.universityschedule.domain.database

import kotlinx.coroutines.flow.Flow
import ru.vafeen.universityschedule.domain.models.Reminder

interface ReminderRepository {
    fun getAsFlowReminders(): Flow<List<Reminder>>
    fun getReminderByIdOfReminder(idOfReminder: Int): Reminder?
    suspend fun insertReminders(reminders: List<Reminder>)
    suspend fun deleteReminders(reminders: List<Reminder>)
    suspend fun updateReminders(reminders: List<Reminder>)
}