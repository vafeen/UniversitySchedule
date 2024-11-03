package ru.vafeen.universityschedule.domain.database

import kotlinx.coroutines.flow.Flow
import ru.vafeen.universityschedule.data.database.entity.LessonEntity
import ru.vafeen.universityschedule.domain.database.entity.ReminderEntity


interface DatabaseRepository {
    fun getAsFlowLessons(): Flow<List<LessonEntity>>
    fun getAsFlowReminders(): Flow<List<ReminderEntity>>
    fun getReminderByIdOfReminder(idOfReminder: Int): ReminderEntity?
    suspend fun insertLessons(lessonEntities: Iterable<LessonEntity>)
    suspend fun insertReminders(reminderEntities: Iterable<ReminderEntity>)
    suspend fun deleteLessons(lessonEntities: Iterable<LessonEntity>)
    suspend fun deleteReminders(reminderEntities: Iterable<ReminderEntity>)
    suspend fun updateLessons(lessonEntities: Iterable<LessonEntity>)
    suspend fun updateReminders(reminderEntities: Iterable<ReminderEntity>)
}