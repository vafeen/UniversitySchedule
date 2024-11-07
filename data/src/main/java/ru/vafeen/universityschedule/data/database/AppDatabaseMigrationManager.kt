package ru.vafeen.universityschedule.data.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

internal class AppDatabaseMigrationManager {
    private val migrations = listOf(
        object : Migration(1, 2) {
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
        },
        object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE `Lesson` ADD COLUMN `idOfReminderAfterBeginningLesson` INTEGER")
                db.execSQL("ALTER TABLE `Lesson` RENAME COLUMN `idOfReminder` TO `idOfReminderBeforeLesson`")
            }
        },
        object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE reminder ADD COLUMN type TEXT NOT NULL DEFAULT 'AFTER_BEGINNING_LESSON'")
            }
        },
        object : Migration(4, 5) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE reminder ADD COLUMN duration TEXT NOT NULL DEFAULT 'EVERY_WEEK'")
            }
        },
        object : Migration(5, 6) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE lesson ADD COLUMN note TEXT DEFAULT NULL")
            }
        },
    )
    fun migrations(): Array<Migration> = migrations.toTypedArray()

}