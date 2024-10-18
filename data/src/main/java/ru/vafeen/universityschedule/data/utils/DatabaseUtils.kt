package ru.vafeen.universityschedule.data.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.universityschedule.data.database.DatabaseRepository
import ru.vafeen.universityschedule.data.database.entity.Lesson

fun cleverUpdatingLessons(newLessons: List<Lesson>) {
    val databaseRepository: DatabaseRepository by inject(
        clazz = DatabaseRepository::class.java
    )
    CoroutineScope(Dispatchers.IO).launch {
        val lastLessons = databaseRepository.getAllAsFlowLessons().first()
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
        databaseRepository.deleteAllLessons(*lastLessons.toTypedArray())
        databaseRepository.insertAllLessons(*result.toTypedArray())
    }
}