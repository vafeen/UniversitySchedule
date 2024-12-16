package ru.vafeen.universityschedule.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.vafeen.universityschedule.data.database.dao.parent.DataAccessObject
import ru.vafeen.universityschedule.data.database.dao.parent.implementation.FlowGetAllImplementation
import ru.vafeen.universityschedule.data.database.entity.ReminderEntity

/**
 * Интерфейс DAO для @Entity [Напоминание][ru.vafeen.universityschedule.data.database.entity.ReminderEntity]
 * Напоминание о паре.
 */
@Dao
internal interface ReminderDao : DataAccessObject<ReminderEntity>, FlowGetAllImplementation<ReminderEntity> {

    /**
     * Получение всех напоминаний о парах в виде Flow
     */
    @Query("select * from reminder")
    override fun getAllAsFlow(): Flow<List<ReminderEntity>>

    /**
     * Получение напоминания по идентификатору напоминания
     * @param idOfReminder [Идентификатор напоминания]
     * @return [ReminderEntity] или null, если напоминание не найдено
     */
    @Query("select * from reminder where idOfReminder=:idOfReminder limit 1")
    fun getReminderByIdOfReminder(idOfReminder: Int): ReminderEntity?
}