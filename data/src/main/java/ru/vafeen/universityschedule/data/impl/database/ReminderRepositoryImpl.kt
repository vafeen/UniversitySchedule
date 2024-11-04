package ru.vafeen.universityschedule.data.impl.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.vafeen.universityschedule.data.converters.ReminderConverter
import ru.vafeen.universityschedule.data.database.AppDatabase
import ru.vafeen.universityschedule.domain.database.ReminderRepository
import ru.vafeen.universityschedule.domain.models.Reminder


internal class ReminderRepositoryImpl(
    private val reminderConverter: ReminderConverter,
    private val db: AppDatabase
) : ReminderRepository {
    private val reminderDao = db.reminderDao()

    override fun getAsFlowReminders(): Flow<List<Reminder>> =
        reminderDao.getAllAsFlow().map {
            reminderConverter.convertABList(it)
        }

    override fun getReminderByIdOfReminder(idOfReminder: Int): Reminder? =
        reminderDao.getReminderByIdOfReminder(idOfReminder)?.let { reminderConverter.convertAB(it) }

    override suspend fun insertReminders(reminders: List<Reminder>) =
        reminderDao.insert(reminders.map { reminderConverter.convertBA(it) })

    override suspend fun deleteReminders(reminders: List<Reminder>) =
        reminderDao.delete(reminders.map { reminderConverter.convertBA(it) })

    override suspend fun updateReminders(reminders: List<Reminder>) =
        reminderDao.update(reminders.map { reminderConverter.convertBA(it) })
}