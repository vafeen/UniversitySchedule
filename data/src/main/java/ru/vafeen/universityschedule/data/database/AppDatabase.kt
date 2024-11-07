package ru.vafeen.universityschedule.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.vafeen.universityschedule.data.converters.DateTimeConverter
import ru.vafeen.universityschedule.data.converters.TimeConverter
import ru.vafeen.universityschedule.data.database.dao.LessonDao
import ru.vafeen.universityschedule.data.database.dao.ReminderDao
import ru.vafeen.universityschedule.data.database.entity.LessonEntity
import ru.vafeen.universityschedule.data.database.entity.ReminderEntity

@Database(
    exportSchema = true,
    entities = [LessonEntity::class, ReminderEntity::class],
    version = 6,
)
@TypeConverters(TimeConverter::class, DateTimeConverter::class)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun lessonDao(): LessonDao

    abstract fun reminderDao(): ReminderDao
}