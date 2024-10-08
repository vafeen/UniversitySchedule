package ru.vafeen.universityschedule.database

import kotlinx.coroutines.flow.Flow
import ru.vafeen.universityschedule.database.entity.Lesson
import ru.vafeen.universityschedule.database.entity.Reminder

/**
 * "Abstract" class for manipulations with database
 */
class DatabaseRepository(db: AppDatabase) {
    private val lessonDao = db.lessonDao()
    private val reminderDao = db.reminderDao()
    fun getAllAsFlowLessons(): Flow<List<Lesson>> = lessonDao.getAllAsFlow()
    fun getAllRemindersAsFlow(): Flow<List<Reminder>> = reminderDao.getAllAsFlow()
    fun getReminderByIdOfReminder(idOfReminder: Int): Reminder? =
        reminderDao.getReminderByIdOfReminder(idOfReminder = idOfReminder)

    fun getByIdOfReminderBeforeLesson(idOfReminder: Int): Lesson? =
        lessonDao.getByIdOfReminderBeforeLesson(idOfReminderBeforeLesson = idOfReminder)

    fun getLessonByIdOfReminderAfterBeginningLesson(idOfReminder: Int): Lesson? =
        lessonDao.getByIdOfReminderAfterBeginningLesson(idOfReminderAfterBeginningLesson = idOfReminder)

    /**
     * Inserting && Updating in database one or more lessons
     * @param entities [Set of entities to put in database]
     */
    suspend fun insertAllLessons(vararg entities: Lesson) = lessonDao.insertAll(entities = entities)
    suspend fun insertAllReminders(vararg entities: Reminder) =
        reminderDao.insertAll(entities = entities)

    /**
     * Deleting from database one or more lessons
     * @param entities [Set of entities to remove from database]
     */
    suspend fun deleteAllLessons(vararg entities: Lesson) = lessonDao.delete(entities = entities)
    suspend fun deleteAllReminders(vararg entities: Reminder) =
        reminderDao.delete(entities = entities)


    /**
     * Updating in database one or more lessons
     * @param entities [Set of entities to update in database]
     */
    suspend fun updateAllLessons(vararg entities: Lesson) = lessonDao.update(entities = entities)
    suspend fun updateAllReminders(vararg entities: Reminder) =
        reminderDao.update(entities = entities)


}