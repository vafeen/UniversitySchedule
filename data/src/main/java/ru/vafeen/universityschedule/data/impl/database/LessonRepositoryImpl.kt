package ru.vafeen.universityschedule.data.impl.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.vafeen.universityschedule.data.converters.LessonConverter
import ru.vafeen.universityschedule.data.database.AppDatabase
import ru.vafeen.universityschedule.domain.database.LessonRepository
import ru.vafeen.universityschedule.domain.models.Lesson

internal class LessonRepositoryImpl(
    private val lessonConverter: LessonConverter,
    private val db: AppDatabase
) : LessonRepository {
    private val lessonDao = db.lessonDao()

    override fun getAsFlowLessons(): Flow<List<Lesson>> =
        lessonDao.getAllAsFlow().map {
            lessonConverter.convertABList(it)
        }

    override suspend fun insertLessons(lessons: List<Lesson>) =
        lessonDao.insert(lessons.map { lessonConverter.convertBA(it) })

    override suspend fun deleteLessons(lessons: List<Lesson>) =
        lessonDao.delete(lessons.map { lessonConverter.convertBA(it) })

    override suspend fun updateLessons(lessons: List<Lesson>) =
        lessonDao.update(lessons.map { lessonConverter.convertBA(it) })
}
