package ru.vafeen.universityschedule.data.database.dao.parent

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update


/**
 * Parent DAO interface with base methods
 */
@Dao
interface DataAccessObject<T> {

    /**
     * Inserting && Updating in database one or more entities
     * @param entity [Set of entities to put in database]
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)// insert && update
    suspend fun insert(entities: Iterable<T>)

    /**
     * Updating in database one or more entities
     * @param entities [Set of entities to update in database]
     */
    @Update
    suspend fun updateAll(entities: Iterable<T>)

    /**
     * Deleting from database one or more entities
     * @param entities [Set of entities to remove from database]
     */
    @Delete
    suspend fun delete(entities: Iterable<T>)

}