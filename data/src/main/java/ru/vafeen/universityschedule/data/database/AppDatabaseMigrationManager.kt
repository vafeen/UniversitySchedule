package ru.vafeen.universityschedule.data.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Менеджер миграций базы данных приложения.
 *
 * Этот класс управляет миграциями базы данных, обеспечивая обновление структуры таблиц
 * при изменении версии базы данных.
 */
internal class AppDatabaseMigrationManager {
    private val migrations = listOf(
        // Миграция с версии 1 на 2
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

                // Добавление нового столбца в таблицу Lesson
                db.execSQL(
                    """
                    ALTER TABLE `Lesson` ADD COLUMN `idOfReminder` INTEGER
                    """.trimIndent()
                )
            }
        },
        // Миграция с версии 2 на 3
        object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE `Lesson` ADD COLUMN `idOfReminderAfterBeginningLesson` INTEGER")
                db.execSQL("ALTER TABLE `Lesson` RENAME COLUMN `idOfReminder` TO `idOfReminderBeforeLesson`")
            }
        },
        // Миграция с версии 3 на 4
        object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE reminder ADD COLUMN type TEXT NOT NULL DEFAULT 'AFTER_BEGINNING_LESSON'")
            }
        },
        // Миграция с версии 4 на 5
        object : Migration(4, 5) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE reminder ADD COLUMN duration TEXT NOT NULL DEFAULT 'EVERY_WEEK'")
            }
        },
        // Миграция с версии 5 на 6
        object : Migration(5, 6) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE lesson ADD COLUMN note TEXT DEFAULT NULL")
            }
        },
    )

    /**
     * Получение массива миграций для использования в базе данных.
     */
    fun migrations(): Array<Migration> = migrations.toTypedArray()
}