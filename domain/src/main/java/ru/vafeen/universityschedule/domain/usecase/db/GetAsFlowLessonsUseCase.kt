package ru.vafeen.universityschedule.domain.usecase.db

import kotlinx.coroutines.flow.Flow
import ru.vafeen.universityschedule.domain.database.DatabaseRepository
import ru.vafeen.universityschedule.domain.models.Lesson
import ru.vafeen.universityschedule.domain.usecase.base.UseCase

class GetAsFlowLessonsUseCase(private val repository: DatabaseRepository) : UseCase {
    fun use(): Flow<Iterable<Lesson>> = repository.getAsFlowLessons()
}
