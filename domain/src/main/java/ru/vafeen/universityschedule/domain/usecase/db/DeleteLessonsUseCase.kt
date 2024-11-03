package ru.vafeen.universityschedule.domain.usecase.db

import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.universityschedule.domain.converters.LessonConverter
import ru.vafeen.universityschedule.domain.database.DatabaseRepository
import ru.vafeen.universityschedule.domain.models.Lesson

class DeleteLessonsUseCase {
    private val repository: DatabaseRepository by inject(clazz = DatabaseRepository::class.java)
    private val lessonConverter: LessonConverter by inject(clazz = LessonConverter::class.java)
    suspend  fun use(vararg lesson: Lesson) =
        repository.deleteLessons(lessonConverter.convertBAList(lesson.toList()))
}