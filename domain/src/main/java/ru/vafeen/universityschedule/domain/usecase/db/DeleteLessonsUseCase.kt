package ru.vafeen.universityschedule.domain.usecase.db

import ru.vafeen.universityschedule.domain.database.DatabaseRepository
import ru.vafeen.universityschedule.domain.models.Lesson
import ru.vafeen.universityschedule.domain.usecase.base.UseCase

class DeleteLessonsUseCase(private val repository: DatabaseRepository) : UseCase {
    suspend fun use(vararg lesson: Lesson) =
        repository.deleteLessons(lesson.toList())
}