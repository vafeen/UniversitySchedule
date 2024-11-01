package ru.vafeen.universityschedule.domain.usecase.db

import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.universityschedule.data.database.DatabaseRepository
import ru.vafeen.universityschedule.domain.converters.LessonConverter
import ru.vafeen.universityschedule.domain.database.models.Lesson

class UpdateLessonsUseCase {
    private val repository: DatabaseRepository by inject(clazz = DatabaseRepository::class.java)
    private val lessonConverter: LessonConverter by inject(clazz = LessonConverter::class.java)

    suspend operator fun invoke(vararg lessons: Lesson) =
        repository.updateLessons(lessonConverter.convertDTOEntityList(lessons.toList()))
}