package ru.vafeen.universityschedule.data.database

import kotlinx.coroutines.flow.Flow
import ru.vafeen.universityschedule.data.database.entity.Lesson
import ru.vafeen.universityschedule.data.database.entity.Reminder


interface DatabaseRepository {

    fun getAllAsFlowLessons(): Flow<List<Lesson>>
    fun getAllAsFlowReminders(): Flow<List<Reminder>>
    fun getReminderByIdOfReminder(idOfReminder: Int): Reminder?

    /**
     * Inserting && Updating in database one or more lessons
     * @param lesson [Set of entities to put in database]
     */
    suspend fun insertAllLessons(vararg lesson: Lesson)
    suspend fun insertAllReminders(vararg reminder: Reminder)

    /**
     * Deleting from database one or more lessons
     * @param lesson [Set of entities to remove from database]
     */
    suspend fun deleteAllLessons(vararg lesson: Lesson)
    suspend fun deleteAllReminders(vararg reminder: Reminder)

    /**
     * Updating in database one or more lessons
     * @param lesson [Set of entities to update in database]
     */
    suspend fun updateAllLessons(vararg lesson: Lesson)
    suspend fun updateAllReminders(vararg reminder: Reminder)
}