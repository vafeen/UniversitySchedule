package ru.vafeen.universityschedule.domain.database.usecase

import kotlinx.coroutines.flow.Flow
import ru.vafeen.universityschedule.data.database.AppDatabase
import ru.vafeen.universityschedule.data.database.entity.Lesson

internal class GetAllAsFlowLessonsUseCase(private val db: AppDatabase) {
    operator fun invoke(): Flow<List<Lesson>> = db.lessonDao().getAllAsFlow()
}