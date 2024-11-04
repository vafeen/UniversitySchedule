package ru.vafeen.universityschedule.domain.usecase.db

import kotlinx.coroutines.flow.Flow
import ru.vafeen.universityschedule.domain.database.LessonRepository
import ru.vafeen.universityschedule.domain.models.Lesson
import ru.vafeen.universityschedule.domain.usecase.base.UseCase

class GetAsFlowLessonsUseCase(private val lessonRepository: LessonRepository) : UseCase {
    fun use(): Flow<Iterable<Lesson>> = lessonRepository.getAsFlowLessons()
}
