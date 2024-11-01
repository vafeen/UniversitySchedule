package ru.vafeen.universityschedule.domain.usecase.db

import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.universityschedule.data.database.DatabaseRepository
import ru.vafeen.universityschedule.domain.converters.LessonConverter
import ru.vafeen.universityschedule.domain.database.models.Lesson

class InsertLessonsUseCase {
    private val repository: DatabaseRepository by inject(clazz = DatabaseRepository::class.java)
    private val lessonConverter: LessonConverter by inject(clazz = LessonConverter::class.java)

    suspend operator fun invoke(vararg lesson: Lesson) =
        repository.insertLessons(lessonConverter.convertDTOEntityList(lesson.toList()))
}