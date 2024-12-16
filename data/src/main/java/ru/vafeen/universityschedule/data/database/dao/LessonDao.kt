package ru.vafeen.universityschedule.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.vafeen.universityschedule.data.database.dao.parent.DataAccessObject
import ru.vafeen.universityschedule.data.database.dao.parent.implementation.FlowGetAllImplementation
import ru.vafeen.universityschedule.data.database.entity.LessonEntity

/**
 * Интерфейс DAO для @Entity [Пара][ru.vafeen.universityschedule.data.database.entity.LessonEntity]
 */
@Dao
internal interface LessonDao : DataAccessObject<LessonEntity>, FlowGetAllImplementation<LessonEntity> {

    /**
     * Получение всех пар в виде Flow
     */
    @Query("select * from lesson")
    override fun getAllAsFlow(): Flow<List<LessonEntity>>

    /**
     * Получение пары по идентификатору напоминания перед парой
     * @param idOfReminderBeforeLesson [Идентификатор напоминания перед парой]
     * @return [LessonEntity] или null, если пара не найдена
     */
    @Query("select * from lesson where idOfReminderBeforeLesson=:idOfReminderBeforeLesson limit 1")
    fun getByIdOfReminderBeforeLesson(idOfReminderBeforeLesson: Int): LessonEntity?

    /**
     * Получение пары по идентификатору напоминания после начала пары
     * @param idOfReminderAfterBeginningLesson [Идентификатор напоминания после начала пары]
     * @return [LessonEntity] или null, если пара не найдена
     */
    @Query("select * from lesson where idOfReminderAfterBeginningLesson=:idOfReminderAfterBeginningLesson limit 1")
    fun getByIdOfReminderAfterBeginningLesson(idOfReminderAfterBeginningLesson: Int): LessonEntity?
}