package ru.vafeen.universityschedule.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.vafeen.universityschedule.data.database.dao.parent.DataAccessObject
import ru.vafeen.universityschedule.data.database.dao.parent.implementation.FlowGetAllImplementation
import ru.vafeen.universityschedule.data.database.entity.LessonEntity

/**
 * DAO interface for @Entity [Lesson][ru.vafeen.universityschedule.data.database.entity.LessonEntity]
 */
@Dao
interface LessonDao : DataAccessObject<LessonEntity>, FlowGetAllImplementation<LessonEntity> {

    @Query("select * from lesson")
    override fun getAllAsFlow(): Flow<List<LessonEntity>>

    @Query("select * from lesson where idOfReminderBeforeLesson=:idOfReminderBeforeLesson limit 1")
    fun getByIdOfReminderBeforeLesson(idOfReminderBeforeLesson: Int): LessonEntity?

    @Query("select * from lesson where idOfReminderAfterBeginningLesson=:idOfReminderAfterBeginningLesson limit 1")
    fun getByIdOfReminderAfterBeginningLesson(idOfReminderAfterBeginningLesson: Int): LessonEntity?

}