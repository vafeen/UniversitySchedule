package ru.vafeen.universityschedule.domain.scheduler

interface SchedulerAPIMigrationManager {
    suspend fun migrate()
}