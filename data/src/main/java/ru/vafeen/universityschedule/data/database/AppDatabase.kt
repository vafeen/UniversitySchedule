package ru.vafeen.universityschedule.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.vafeen.universityschedule.data.database.entity.LessonEntity
import ru.vafeen.universityschedule.data.database.entity.ReminderEntity
import ru.vafeen.universityschedule.data.converters.DateTimeConverter
import ru.vafeen.universityschedule.data.converters.TimeConverter

@Database(
    exportSchema = true,
    entities = [LessonEntity::class, ReminderEntity::class],
    version = 5,
)
@TypeConverters(TimeConverter::class, DateTimeConverter::class)
internal abstract class AppDatabase : RoomDatabase() {
    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Создание новой таблицы Reminder
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `Reminder` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `idOfReminder` INTEGER NOT NULL,
                        `title` TEXT NOT NULL,
                        `text` TEXT NOT NULL,
                        `dt` INTEGER NOT NULL
                    )
                    """.trimIndent()
                )

                db.execSQL(
                    """
                    ALTER TABLE `Lesson` ADD COLUMN `idOfReminder` INTEGER
                    """.trimIndent()
                )
            }
        }
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE `Lesson` ADD COLUMN `idOfReminderAfterBeginningLesson` INTEGER")
                db.execSQL("ALTER TABLE `Lesson` RENAME COLUMN `idOfReminder` TO `idOfReminderBeforeLesson`")
            }
        }
        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE reminder ADD COLUMN type TEXT NOT NULL DEFAULT 'AFTER_BEGINNING_LESSON'")
            }
        }
        val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE reminder ADD COLUMN duration TEXT NOT NULL DEFAULT 'EVERY_WEEK'")
            }
        }
    }

    abstract fun lessonDao(): ru.vafeen.universityschedule.data.database.dao.LessonDao

    abstract fun reminderDao(): ru.vafeen.universityschedule.data.database.dao.ReminderDao
}