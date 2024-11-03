package ru.vafeen.universityschedule.domain.database

import kotlinx.coroutines.flow.Flow
import ru.vafeen.universityschedule.domain.models.Lesson
import ru.vafeen.universityschedule.domain.models.Reminder


interface DatabaseRepository {
    fun getAsFlowLessons(): Flow<List<Lesson>>
    fun getAsFlowReminders(): Flow<List<Reminder>>
    fun getReminderByIdOfReminder(idOfReminder: Int): Reminder?
    suspend fun insertLessons(lessons: List<Lesson>)
    suspend fun insertReminders(reminders: List<Reminder>)
    suspend fun deleteLessons(lessons: List<Lesson>)
    suspend fun deleteReminders(reminders: List<Reminder>)
    suspend fun updateLessons(lessons: List<Lesson>)
    suspend fun updateReminders(reminders: List<Reminder>)
}