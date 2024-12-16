package ru.vafeen.universityschedule.data.impl.scheduler

import android.content.Context
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import ru.vafeen.universityschedule.data.converters.DateTimeConverter
import ru.vafeen.universityschedule.domain.models.Reminder
import ru.vafeen.universityschedule.domain.scheduler.Scheduler
import ru.vafeen.universityschedule.domain.scheduler.SchedulerExtra
import ru.vafeen.universityschedule.domain.utils.getUniqueReminderWorkID
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

/**
 * Реализация интерфейса [Scheduler] для планирования задач с использованием WorkManager.
 *
 * Данный класс предоставляет методы для планирования повторяющихся и одноразовых задач напоминаний,
 * а также для их отмены.
 *
 * @property context Контекст приложения, используемый для инициализации WorkManager.
 * @property dtConverter Конвертер для обработки дат и времени.
 */
internal class SchedulerImpl(
    private val context: Context,
    private val dtConverter: DateTimeConverter
) : Scheduler {

    // Экземпляр WorkManager для управления задачами.
    private val workManager = WorkManager.getInstance(context)

    /**
     * Планирует повторяющуюся задачу для напоминания с эластичным интервалом.
     *
     * @param reminder Напоминание с данными о времени и длительности.
     */
    override fun scheduleRepeatingJob(reminder: Reminder) {
        // Создаем запрос на повторяющуюся задачу с эластичным интервалом.
        val repeatingWorkRequest = PeriodicWorkRequestBuilder<ReminderWorker>(
            reminder.duration.duration.milliSeconds, // Интервал повторения задачи.
            TimeUnit.MILLISECONDS
        )
            .setConstraints(createConstraints()) // Устанавливаем ограничения.
            .setInitialDelay(
                calculateInitialDelay(reminder),
                TimeUnit.MILLISECONDS
            ) // Задержка перед первым запуском.
            .setInputData(createInputData(reminder)) // Передаем данные в Worker.
            .build()

        // Добавляем задачу в WorkManager с уникальным идентификатором.
        workManager.enqueueUniquePeriodicWork(
            reminder.getUniqueReminderWorkID(), // Уникальный идентификатор задачи.
            ExistingPeriodicWorkPolicy.REPLACE, // Политика замены существующей задачи.
            repeatingWorkRequest
        )
    }

    /**
     * Планирует одноразовую задачу, если требуется точное выполнение.
     *
     * @param reminder Напоминание с данными о времени.
     */
    override fun scheduleOneTimeJob(reminder: Reminder) {
        // Создаем объект данных, передаваемых в Worker.
        val data = createInputData(reminder)

        // Вычисляем задержку перед запуском задачи.
        val initialDelay = calculateInitialDelay(reminder)

        // Создаем ограничения для задачи.
        val constraints = createConstraints()

        // Создаем запрос на одноразовую задачу.
        val oneTimeWorkRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setConstraints(constraints) // Устанавливаем ограничения.
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS) // Задержка перед запуском.
            .setInputData(data) // Передаем данные в Worker.
            .build()

        // Добавляем задачу в WorkManager.
        workManager.enqueueUniqueWork(
            reminder.getUniqueReminderWorkID(), // Уникальный идентификатор задачи.
            ExistingWorkPolicy.REPLACE, // Политика замены существующей задачи.
            oneTimeWorkRequest
        )
    }

    /**
     * Отменяет задачу, связанную с заданным напоминанием.
     *
     * @param reminder Напоминание, для которого нужно отменить задачу.
     */
    override fun cancelJob(reminder: Reminder) {
        // Отменяем задачу в WorkManager по уникальному идентификатору задачи.
        workManager.cancelUniqueWork(reminder.getUniqueReminderWorkID())
    }

    override fun cancelAll() {
        workManager.cancelAllWork()
    }

    /**
     * Вычисляет задержку перед запуском задачи на основе текущего времени и времени напоминания.
     *
     * @param reminder Напоминание с указанием времени.
     * @return Задержка в миллисекундах.
     */
    private fun calculateInitialDelay(reminder: Reminder): Long {
        val now = dtConverter.convertAB(LocalDateTime.now()) // Текущее время в миллисекундах.
        val reminderTime = dtConverter.convertAB(reminder.dt) // Время напоминания в миллисекундах.
        return if (reminderTime > now) reminderTime - now else 0L // Вычисляем разницу между временем напоминания и текущим временем.
    }

    /**
     * Создает объект данных, передаваемых в Worker для указанного напоминания.
     *
     * @param reminder Напоминание, для которого создаются данные.
     * @return Объект Data с данными о напоминании.
     */
    private fun createInputData(reminder: Reminder): Data {
        return Data.Builder()
            .putInt(SchedulerExtra.ID_OF_REMINDER, reminder.idOfReminder)
            .build()
    }

    /**
     * Создает ограничения для задач WorkManager.
     *
     * @return Объект Constraints с установленными ограничениями для выполнения задач.
     */
    private fun createConstraints(): Constraints {
        return Constraints.Builder()
            .setRequiresBatteryNotLow(false) // Разрешить выполнение при низком заряде батареи.
            .setRequiresDeviceIdle(false) // Разрешить выполнение, даже если устройство не в состоянии покоя.
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED) // Не требовать подключения к сети.
            .build()
    }

}