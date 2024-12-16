package ru.vafeen.universityschedule.data.database.dao.parent

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

/**
 * Родительский интерфейс DAO с базовыми методами
 */
@Dao
internal interface DataAccessObject<T> {

    /**
     * Вставка и обновление одной или нескольких сущностей в базе данных
     * @param entities [Набор сущностей для добавления в базу данных]
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE) // вставка и обновление
    suspend fun insert(entities: List<T>)

    /**
     * Обновление одной или нескольких сущностей в базе данных
     * @param entities [Набор сущностей для обновления в базе данных]
     */
    @Update
    suspend fun update(entities: List<T>)

    /**
     * Удаление одной или нескольких сущностей из базы данных
     * @param entities [Набор сущностей для удаления из базы данных]
     */
    @Delete
    suspend fun delete(entities: List<T>)
}