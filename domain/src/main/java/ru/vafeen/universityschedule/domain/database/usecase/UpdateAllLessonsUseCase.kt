package ru.vafeen.universityschedule.domain.database.usecase

import ru.vafeen.universityschedule.data.database.AppDatabase
import ru.vafeen.universityschedule.data.database.entity.Lesson

class UpdateAllLessonsUseCase(private val db: AppDatabase) {
    suspend operator fun invoke(vararg lesson: Lesson) = db.lessonDao().update(entity = lesson)
}