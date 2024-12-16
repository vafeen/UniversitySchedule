package ru.vafeen.universityschedule.domain.scheduler

/**
 * Интерфейс для управления миграцией API планировщика.
 */
interface SchedulerAPIMigrationManager {

    /**
     * Выполняет миграцию данных или настроек для API планировщика.
     *
     * Этот метод должен быть вызван в корутине, так как он является приостановленным.
     */
    suspend fun migrate()
}
