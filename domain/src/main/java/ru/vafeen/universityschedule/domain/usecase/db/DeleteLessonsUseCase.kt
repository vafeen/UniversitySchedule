package ru.vafeen.universityschedule.domain.usecase.db

import ru.vafeen.universityschedule.domain.database.LessonRepository
import ru.vafeen.universityschedule.domain.models.Lesson
import ru.vafeen.universityschedule.domain.usecase.base.UseCase

class DeleteLessonsUseCase(private val lessonRepository: LessonRepository) : UseCase {
    suspend fun use(vararg lesson: Lesson) =
        lessonRepository.deleteLessons(lesson.toList())
}