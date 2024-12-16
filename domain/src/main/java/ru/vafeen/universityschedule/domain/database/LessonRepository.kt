package ru.vafeen.universityschedule.domain.database

import kotlinx.coroutines.flow.Flow
import ru.vafeen.universityschedule.domain.models.Lesson

/**
 * Интерфейс репозитория для работы с уроками.
 */
interface LessonRepository {

    /**
     * Получает список уроков как поток данных.
     *
     * @return [Flow] списка объектов [Lesson].
     */
    fun getAsFlowLessons(): Flow<List<Lesson>>

    /**
     * Вставляет список уроков в базу данных.
     *
     * @param lessons Список объектов [Lesson], которые нужно вставить.
     */
    suspend fun insertLessons(lessons: List<Lesson>)

    /**
     * Удаляет список уроков из базы данных.
     *
     * @param lessons Список объектов [Lesson], которые нужно удалить.
     */
    suspend fun deleteLessons(lessons: List<Lesson>)

    /**
     * Обновляет список уроков в базе данных.
     *
     * @param lessons Список объектов [Lesson], которые нужно обновить.
     */
    suspend fun updateLessons(lessons: List<Lesson>)
}
