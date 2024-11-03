package ru.vafeen.universityschedule.domain.usecase.db

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.universityschedule.domain.converters.LessonConverter
import ru.vafeen.universityschedule.domain.database.DatabaseRepository
import ru.vafeen.universityschedule.domain.models.Lesson

class GetAsFlowLessonsUseCase {
    private val repository: DatabaseRepository by inject(clazz = DatabaseRepository::class.java)
    private val lessonConverter: LessonConverter by inject(clazz = LessonConverter::class.java)
    fun use(): Flow<Iterable<Lesson>> =
        repository.getAsFlowLessons().map { lessonEntities ->
            lessonConverter.convertABList(lessonEntities)
        }
}
