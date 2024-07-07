package ru.vafeen.universityschedule.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.vafeen.universityschedule.database.dao.parent.DataAccessObject
import ru.vafeen.universityschedule.database.dao.parent.implementation.FlowGetAllImplementation
import ru.vafeen.universityschedule.database.entity.Lesson

/**
 * DAO interface for @Entity [Lesson][ru.vafeen.universityschedule.database.entity.Lesson]
 */
@Dao
interface LessonDao : DataAccessObject<Lesson>, FlowGetAllImplementation<Lesson> {

    @Query("select * from lesson")
    override fun getAllAsFlow(): Flow<List<Lesson>>

}