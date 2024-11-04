package ru.vafeen.universityschedule.domain.database

import kotlinx.coroutines.flow.Flow
import ru.vafeen.universityschedule.domain.models.Lesson

interface LessonRepository {
    fun getAsFlowLessons(): Flow<List<Lesson>>
    suspend fun insertLessons(lessons: List<Lesson>)
    suspend fun deleteLessons(lessons: List<Lesson>)
    suspend fun updateLessons(lessons: List<Lesson>)
}