package ru.vafeen.universityschedule.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.vafeen.universityschedule.database.converters.LessonTypeConverters
import ru.vafeen.universityschedule.database.dao.LessonDao
import ru.vafeen.universityschedule.database.dao.ReminderDao
import ru.vafeen.universityschedule.database.entity.Lesson
import ru.vafeen.universityschedule.database.entity.Reminder

@Database(
    exportSchema = true,
    entities = [Lesson::class, Reminder::class],
    version = 5,
)
@TypeConverters(LessonTypeConverters::class, DTConverters::class)
abstract class AppDatabase : RoomDatabase() {
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
                db.execSQL("ALTER TABLE  ADD COLUMN type TEXT NOT NULL DEFAULT 'AFTER_BEGINNING_LESSON'")
            }
        }
        val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE reminder ADD COLUMN duration TEXT NOT NULL DEFAULT 'EVERY_WEEK'")
            }
        }
    }

    abstract fun lessonDao(): LessonDao

    abstract fun reminderDao(): ReminderDao
}