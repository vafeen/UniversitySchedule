package ru.vafeen.universityschedule.data.database.dao.parent.implementation

import kotlinx.coroutines.flow.Flow

/**
 * Интерфейс дополнений для базового [DataAccessObject][ru.vafeen.universityschedule.data.database.dao.parent.DataAccessObject]
 *
 * Дополнение: получение всех сущностей T в виде Flow
 */
internal interface FlowGetAllImplementation<T> {
    /**
     * Получение всех сущностей T в виде Flow
     */
    fun getAllAsFlow(): Flow<List<T>>
}