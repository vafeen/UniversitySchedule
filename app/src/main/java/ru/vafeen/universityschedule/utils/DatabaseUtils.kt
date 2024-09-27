package ru.vafeen.universityschedule.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.universityschedule.database.DatabaseRepository
import ru.vafeen.universityschedule.database.entity.Lesson

fun cleverUpdatingLessons(newLessons: List<Lesson>) {
    val databaseRepository: DatabaseRepository by inject(
        clazz = DatabaseRepository::class.java
    )
    CoroutineScope(Dispatchers.IO).launch {
        val lastLessons = databaseRepository.getAllAsFlowLessons().first()
        val result = mutableListOf<Lesson>()
        for (newLesson in newLessons) {
            val newLessonInLastArray = lastLessons.filter {
                it.dayOfWeek == newLesson.dayOfWeek &&
                        it.name == newLesson.name &&
                        it.startTime == newLesson.startTime &&
                        it.endTime == newLesson.endTime &&
                        it.classroom == newLesson.classroom &&
                        it.teacher == newLesson.teacher &&
                        it.subGroup == newLesson.subGroup &&
                        it.frequency == newLesson.frequency
            }
            result.add(
                if (newLessonInLastArray.isNotEmpty() && newLessonInLastArray.size == 1)
                    newLessonInLastArray[0]
                else newLesson
            )
        }
        databaseRepository.deleteAllLessons(*lastLessons.toTypedArray())
        databaseRepository.insertAllLessons(*result.toTypedArray())
    }
}