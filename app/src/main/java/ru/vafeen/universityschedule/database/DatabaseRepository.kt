package ru.vafeen.universityschedule.database

import kotlinx.coroutines.flow.Flow
import ru.vafeen.universityschedule.database.dao.LessonDao
import ru.vafeen.universityschedule.database.entity.Lesson
import javax.inject.Inject

class DatabaseRepository @Inject constructor(
    private val lessonDao: LessonDao
) {

    fun getAllAsFlowLessons(): Flow<List<Lesson>> = lessonDao.getAllAsFlow()

    suspend fun insertAllLessons(vararg entities: Lesson) = lessonDao.insertAll(entities = entities)

    suspend fun deleteAllLessons(vararg entities: Lesson) = lessonDao.delete(entities = entities)

    suspend fun updateAllLessons(vararg entities: Lesson) = lessonDao.update(entities = entities)

}