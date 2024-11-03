package ru.vafeen.universityschedule.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.vafeen.universityschedule.data.database.dao.parent.DataAccessObject
import ru.vafeen.universityschedule.data.database.dao.parent.implementation.FlowGetAllImplementation
import ru.vafeen.universityschedule.data.database.entity.ReminderEntity

@Dao
internal interface ReminderDao : DataAccessObject<ReminderEntity>, FlowGetAllImplementation<ReminderEntity> {
    @Query("select * from reminder")
    override fun getAllAsFlow(): Flow<List<ReminderEntity>>

    @Query("select * from reminder where idOfReminder=:idOfReminder limit 1")
    fun getReminderByIdOfReminder(idOfReminder: Int): ReminderEntity?
}