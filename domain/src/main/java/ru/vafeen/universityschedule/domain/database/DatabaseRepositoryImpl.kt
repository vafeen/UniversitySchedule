package ru.vafeen.universityschedule.domain.database

import kotlinx.coroutines.flow.Flow
import ru.vafeen.universityschedule.data.database.DatabaseRepository
import ru.vafeen.universityschedule.data.database.entity.Lesson
import ru.vafeen.universityschedule.data.database.entity.Reminder
import ru.vafeen.universityschedule.domain.database.usecase.DeleteAllLessonsUseCase
import ru.vafeen.universityschedule.domain.database.usecase.DeleteAllRemindersUseCase
import ru.vafeen.universityschedule.domain.database.usecase.GetAllAsFlowLessonsUseCase
import ru.vafeen.universityschedule.domain.database.usecase.GetAllAsFlowRemindersUseCase
import ru.vafeen.universityschedule.domain.database.usecase.GetReminderByIdOfReminderUseCase
import ru.vafeen.universityschedule.domain.database.usecase.InsertAllLessonsUseCase
import ru.vafeen.universityschedule.domain.database.usecase.InsertAllRemindersUseCase
import ru.vafeen.universityschedule.domain.database.usecase.UpdateAllLessonsUseCase
import ru.vafeen.universityschedule.domain.database.usecase.UpdateAllRemindersUseCase

internal class DatabaseRepositoryImpl(
    private val getAllAsFlowLessonsUseCase: GetAllAsFlowLessonsUseCase,
    private val getAllAsFlowRemindersUseCase: GetAllAsFlowRemindersUseCase,
    private val getReminderByIdOfReminderUseCase: GetReminderByIdOfReminderUseCase,
    private val insertAllLessonsUseCase: InsertAllLessonsUseCase,
    private val insertAllRemindersUseCase: InsertAllRemindersUseCase,
    private val deleteAllLessonsUseCase: DeleteAllLessonsUseCase,
    private val deleteAllRemindersUseCase: DeleteAllRemindersUseCase,
    private val updateAllLessonsUseCase: UpdateAllLessonsUseCase,
    private val updateAllRemindersUseCase: UpdateAllRemindersUseCase
) : DatabaseRepository {

    override fun getAllAsFlowLessons(): Flow<List<Lesson>> = getAllAsFlowLessonsUseCase()
    override fun getAllAsFlowReminders(): Flow<List<Reminder>> = getAllAsFlowRemindersUseCase()

    override fun getReminderByIdOfReminder(idOfReminder: Int): Reminder? =
        getReminderByIdOfReminderUseCase(idOfReminder = idOfReminder)

    /**
     * Inserting && Updating in database one or more lessons
     * @param lesson [Set of entities to put in database]
     */
    override suspend fun insertAllLessons(vararg lesson: Lesson) =
        insertAllLessonsUseCase(lesson = lesson)

    override suspend fun insertAllReminders(vararg reminder: Reminder) =
        insertAllRemindersUseCase(reminder = reminder)

    /**
     * Deleting from database one or more lessons
     * @param lesson [Set of entities to remove from database]
     */
    override suspend fun deleteAllLessons(vararg lesson: Lesson) =
        deleteAllLessonsUseCase(lesson = lesson)

    override suspend fun deleteAllReminders(vararg reminder: Reminder) =
        deleteAllRemindersUseCase(reminder = reminder)


    /**
     * Updating in database one or more lessons
     * @param lesson [Set of entities to update in database]
     */
    override suspend fun updateAllLessons(vararg lesson: Lesson) =
        updateAllLessonsUseCase(lesson = lesson)

    override suspend fun updateAllReminders(vararg reminder: Reminder) =
        updateAllRemindersUseCase(reminder = reminder)

}