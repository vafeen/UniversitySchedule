package ru.vafeen.universityschedule.database.dao.parent.implementation

import kotlinx.coroutines.flow.Flow

/**
 * Interface of additions for basic [DataAccessObject][ru.vafeen.universityschedule.database.dao.parent.DataAccessObject]
 *
 * Addition: getting all entities T as Flow
 */
interface FlowGetAllImplementation<T> {
    /**
     * Getting all entities T as Flow
     */
    fun getAllAsFlow(): Flow<List<T>>
}