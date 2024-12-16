package ru.vafeen.universityschedule.domain.database

import kotlinx.coroutines.flow.Flow
import ru.vafeen.universityschedule.domain.models.Reminder

/**
 * Интерфейс репозитория для работы с напоминаниями.
 */
interface ReminderRepository {

    /**
     * Получает список напоминаний как поток данных.
     *
     * @return [Flow] списка объектов [Reminder].
     */
    fun getAsFlowReminders(): Flow<List<Reminder>>

    /**
     * Получает напоминание по его идентификатору.
     *
     * @param idOfReminder Идентификатор напоминания.
     * @return Объект [Reminder] с указанным идентификатором или null, если не найдено.
     */
    fun getReminderByIdOfReminder(idOfReminder: Int): Reminder?

    /**
     * Вставляет список напоминаний в базу данных.
     *
     * @param reminders Список объектов [Reminder], которые нужно вставить.
     */
    suspend fun insertReminders(reminders: List<Reminder>)

    /**
     * Удаляет список напоминаний из базы данных.
     *
     * @param reminders Список объектов [Reminder], которые нужно удалить.
     */
    suspend fun deleteReminders(reminders: List<Reminder>)

    /**
     * Обновляет список напоминаний в базе данных.
     *
     * @param reminders Список объектов [Reminder], которые нужно обновить.
     */
    suspend fun updateReminders(reminders: List<Reminder>)
}