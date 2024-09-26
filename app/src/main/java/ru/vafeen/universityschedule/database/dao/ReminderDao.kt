package ru.vafeen.universityschedule.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.vafeen.universityschedule.database.dao.parent.DataAccessObject
import ru.vafeen.universityschedule.database.dao.parent.implementation.FlowGetAllImplementation
import ru.vafeen.universityschedule.database.entity.Reminder

@Dao
interface ReminderDao : DataAccessObject<Reminder>, FlowGetAllImplementation<Reminder> {
    @Query("select * from reminder")
    override fun getAllAsFlow(): Flow<List<Reminder>>

}