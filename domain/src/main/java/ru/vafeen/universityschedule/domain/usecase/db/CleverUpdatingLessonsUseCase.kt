package ru.vafeen.universityschedule.domain.usecase.db

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.universityschedule.data.database.entity.LessonEntity
import ru.vafeen.universityschedule.domain.converters.LessonConverter
import ru.vafeen.universityschedule.domain.database.models.Lesson
import ru.vafeen.universityschedule.domain.utils.containsLesson

class CleverUpdatingLessonsUseCase(
    private val getAsFlowLessonsUseCase: GetAsFlowLessonsUseCase,
    private val insertLessonsUseCase: InsertLessonsUseCase,
    private val deleteLessonsUseCase: DeleteLessonsUseCase,
) {
    private val lessonConverter: LessonConverter by inject(clazz = LessonConverter::class.java)
    operator fun invoke(newLessonEntities: List<LessonEntity>) {
        CoroutineScope(Dispatchers.IO).launch {
            val newLessons = lessonConverter.convertEntityDTOList(newLessonEntities)
            val lastLessons = getAsFlowLessonsUseCase().first()
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

            insertLessonsUseCase.invoke(*result.toTypedArray())
            deleteLessonsUseCase.invoke(*resultForDelete.toTypedArray())
        }
    }
}