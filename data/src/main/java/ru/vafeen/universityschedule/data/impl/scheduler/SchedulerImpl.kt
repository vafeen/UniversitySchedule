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
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

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
        // Создаем объект данных, передаваемых в Worker.
        val data = Data.Builder()
            .putInt(SchedulerExtra.ID_OF_REMINDER, reminder.idOfReminder) // ID напоминания.
            .build()

        // Настраиваем ограничения для задачи, чтобы она выполнялась в любых условиях.
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(false) // Разрешить выполнение даже при низком заряде батареи.
            .setRequiresDeviceIdle(false) // Разрешить выполнение, даже если устройство не в состоянии покоя.
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED) // Не требовать подключения к сети.
            .build()

        // Создаем запрос на повторяющуюся задачу с эластичным интервалом.
        val repeatingWorkRequest = PeriodicWorkRequestBuilder<ReminderWorker>(
            reminder.duration.duration.milliSeconds, // Интервал повторения задачи.
            TimeUnit.MILLISECONDS,
            flexInterval.inWholeMilliseconds, // Эластичный интервал для гибкости.
            TimeUnit.MILLISECONDS
        )
            .setConstraints(constraints) // Устанавливаем ограничения.
            .setInitialDelay(
                calculateInitialDelay(reminder),
                TimeUnit.MILLISECONDS
            ) // Задержка перед первым запуском.
            .setInputData(data) // Передаем данные в Worker.
            .build()

        // Добавляем задачу в WorkManager с уникальным идентификатором.
        workManager.enqueueUniquePeriodicWork(
            reminder.getUniqueReminderWorkID(), // Уникальный идентификатор задачи.
            ExistingPeriodicWorkPolicy.UPDATE, // Политика замены существующей задачи.
            repeatingWorkRequest
        )
    }

    /**
     * Планирует одноразовую задачу, если требуется точное выполнение.
     *
     * @param reminder Напоминание с данными о времени.
     */
    fun scheduleOneTimeJob(reminder: Reminder) {
        // Создаем объект данных, передаваемых в Worker.
        val data = Data.Builder()
            .putInt(SchedulerExtra.ID_OF_REMINDER, reminder.idOfReminder) // ID напоминания.
            .build()

        // Вычисляем задержку перед запуском задачи.
        val initialDelay = calculateInitialDelay(reminder)

        // Настраиваем ограничения для задачи.
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(false) // Разрешить выполнение при низком заряде батареи.
            .setRequiresDeviceIdle(false) // Разрешить выполнение, даже если устройство не в состоянии покоя.
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED) // Не требовать подключения к сети.
            .build()

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

    /**
     * Вычисляет задержку перед запуском задачи на основе текущего времени и времени напоминания.
     *
     * @param reminder Напоминание с указанием времени.
     * @return Задержка в миллисекундах.
     */
    private fun calculateInitialDelay(reminder: Reminder): Long {
        val now = System.currentTimeMillis() // Текущее время в миллисекундах.
        val reminderTime = dtConverter.convertAB(reminder.dt) // Время напоминания в миллисекундах.
        return if (reminderTime > now) reminderTime - now else 0L // Вычисляем разницу между временем напоминания и текущим временем.
    }

    /**
     * Эластичный интервал повторения задачи.
     * Задает минимальное время, которое WorkManager может подождать для выполнения задачи в гибком режиме.
     */
    private val flexInterval: Duration = 1.minutes
}