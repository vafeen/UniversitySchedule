package ru.vafeen.universityschedule.database

import kotlinx.coroutines.flow.Flow
import ru.vafeen.universityschedule.database.entity.Lesson
import javax.inject.Inject

/**
 * "Abstract" class for manipulations with database
 */
class DatabaseRepository @Inject constructor(db: AppDatabase) {
    private val lessonDao = db.lessonDao()
    fun getAllAsFlowLessons(): Flow<List<Lesson>> = lessonDao.getAllAsFlow()

    /**
     * Inserting && Updating in database one or more lessons
     * @param entities [Set of entities to put in database]
     */
    suspend fun insertAllLessons(vararg entities: Lesson) = lessonDao.insertAll(entities = entities)

    /**
     * Deleting from database one or more lessons
     * @param entities [Set of entities to remove from database]
     */
    suspend fun deleteAllLessons(vararg entities: Lesson) = lessonDao.delete(entities = entities)

    /**
     * Updating in database one or more lessons
     * @param entities [Set of entities to update in database]
     */
    suspend fun updateAllLessons(vararg entities: Lesson) = lessonDao.update(entities = entities)
}