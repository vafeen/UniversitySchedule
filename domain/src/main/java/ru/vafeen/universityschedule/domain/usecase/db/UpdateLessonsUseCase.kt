package ru.vafeen.universityschedule.domain.usecase.db

import ru.vafeen.universityschedule.domain.database.LessonRepository
import ru.vafeen.universityschedule.domain.models.Lesson
import ru.vafeen.universityschedule.domain.usecase.base.UseCase

class UpdateLessonsUseCase(private val lessonRepository: LessonRepository) : UseCase {
    suspend fun invoke(vararg lessons: Lesson) =
        lessonRepository.updateLessons(lessons.toList())
}