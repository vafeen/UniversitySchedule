package ru.vafeen.universityschedule.domain.usecase.db

import ru.vafeen.universityschedule.domain.database.DatabaseRepository
import ru.vafeen.universityschedule.domain.models.Lesson
import ru.vafeen.universityschedule.domain.usecase.base.UseCase

class UpdateLessonsUseCase(private val repository: DatabaseRepository) : UseCase {
    suspend fun use(vararg lessons: Lesson) =
        repository.updateLessons(lessons.toList())
}