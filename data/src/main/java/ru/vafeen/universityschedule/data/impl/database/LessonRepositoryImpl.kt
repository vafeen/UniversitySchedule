package ru.vafeen.universityschedule.data.impl.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.vafeen.universityschedule.data.converters.LessonConverter
import ru.vafeen.universityschedule.data.database.AppDatabase
import ru.vafeen.universityschedule.domain.database.LessonRepository
import ru.vafeen.universityschedule.domain.models.Lesson

/**
 * Реализация репозитория для работы с парами в базе данных.
 *
 * @property lessonConverter Конвертер для преобразования объектов Lesson между слоями.
 * @property db База данных приложения.
 */
internal class LessonRepositoryImpl(
    private val lessonConverter: LessonConverter,
    private val db: AppDatabase
) : LessonRepository {

    private val lessonDao = db.lessonDao()

    /**
     * Получение списка пар в виде Flow.
     *
     * @return Flow, содержащий список пар.
     */
    override fun getAsFlowLessons(): Flow<List<Lesson>> =
        lessonDao.getAllAsFlow().map {
            lessonConverter.convertABList(it)
        }

    /**
     * Вставка списка пар в базу данных.
     *
     * @param lessons Список пар для вставки.
     */
    override suspend fun insertLessons(lessons: List<Lesson>) =
        lessonDao.insert(lessons.map { lessonConverter.convertBA(it) })

    /**
     * Удаление списка пар из базы данных.
     *
     * @param lessons Список пар для удаления.
     */
    override suspend fun deleteLessons(lessons: List<Lesson>) =
        lessonDao.delete(lessons.map { lessonConverter.convertBA(it) })

    /**
     * Обновление списка пар в базе данных.
     *
     * @param lessons Список пар для обновления.
     */
    override suspend fun updateLessons(lessons: List<Lesson>) =
        lessonDao.update(lessons.map { lessonConverter.convertBA(it) })
}