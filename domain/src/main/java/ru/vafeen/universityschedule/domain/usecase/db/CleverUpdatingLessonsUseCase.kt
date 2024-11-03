package ru.vafeen.universityschedule.domain.usecase.db

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.vafeen.universityschedule.domain.models.Lesson
import ru.vafeen.universityschedule.domain.usecase.base.UseCase
import ru.vafeen.universityschedule.domain.utils.containsLesson

class CleverUpdatingLessonsUseCase(
    private val getAsFlowLessonsUseCase: GetAsFlowLessonsUseCase,
    private val insertLessonsUseCase: InsertLessonsUseCase,
    private val deleteLessonsUseCase: DeleteLessonsUseCase,
) : UseCase {
    fun use(newLessons: List<Lesson>) {
        CoroutineScope(Dispatchers.IO).launch {
            val lastLessons = getAsFlowLessonsUseCase.use().first()
            val result = mutableListOf<Lesson>()
            val resultForDelete = mutableListOf<Lesson>()
            for (newLesson in newLessons) {
                result.add(lastLessons.containsLesson(lesson = newLesson) ?: newLesson)
            }
            for (lastLesson in lastLessons) {
                newLessons.containsLesson(lesson = lastLesson).let {
                    if (it == null)
                        resultForDelete.add(lastLesson)
                }
            }

            insertLessonsUseCase.use(*result.toTypedArray())
            deleteLessonsUseCase.use(*resultForDelete.toTypedArray())
        }
    }
}