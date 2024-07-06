package ru.vafeen.universityschedule.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.vafeen.universityschedule.database.converters.LessonTypeConverters
import ru.vafeen.universityschedule.database.dao.LessonDao
import ru.vafeen.universityschedule.database.entity.Lesson

@Database(entities = [Lesson::class], version = 1)
@TypeConverters(LessonTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun lessonDao(): LessonDao

}