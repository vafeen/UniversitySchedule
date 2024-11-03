package ru.vafeen.universityschedule.data.database

import kotlinx.coroutines.flow.Flow
import ru.vafeen.universityschedule.data.database.entity.LessonEntity
import ru.vafeen.universityschedule.domain.database.AppDatabase
import ru.vafeen.universityschedule.domain.database.DatabaseRepository
import ru.vafeen.universityschedule.domain.database.entity.ReminderEntity

internal class DatabaseRepositoryImpl(private val db: AppDatabase) : DatabaseRepository {
    private val lessonDao = db.lessonDao()
    private val reminderDao = db.reminderDao()

    override fun getAsFlowLessons(): Flow<List<LessonEntity>> =
        lessonDao.getAllAsFlow()

    override fun getAsFlowReminders(): Flow<List<ReminderEntity>> =
        reminderDao.getAllAsFlow()

    override fun getReminderByIdOfReminder(idOfReminder: Int): ReminderEntity? =
        reminderDao.getReminderByIdOfReminder(idOfReminder)

    override suspend fun insertLessons(lessonEntities: Iterable<LessonEntity>) =
        lessonDao.insert(lessonEntities)

    override suspend fun insertReminders(reminderEntities: Iterable<ReminderEntity>) =
        reminderDao.insert(reminderEntities)

    override suspend fun deleteLessons(lessonEntities: Iterable<LessonEntity>) =
        lessonDao.delete(lessonEntities)

    override suspend fun deleteReminders(reminderEntities: Iterable<ReminderEntity>) =
        reminderDao.delete(reminderEntities)

    override suspend fun updateLessons(lessonEntities: Iterable<LessonEntity>) =
        lessonDao.updateAll(lessonEntities)

    override suspend fun updateReminders(reminderEntities: Iterable<ReminderEntity>) =
        reminderDao.updateAll(reminderEntities)
}