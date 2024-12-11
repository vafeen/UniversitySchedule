package ru.vafeen.universityschedule.data.impl.scheduler

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import ru.vafeen.universityschedule.data.converters.DateTimeConverter
import ru.vafeen.universityschedule.domain.models.Reminder
import ru.vafeen.universityschedule.domain.scheduler.Scheduler
import ru.vafeen.universityschedule.domain.scheduler.SchedulerExtra
import ru.vafeen.universityschedule.domain.utils.getUniqueReminderWorkID
import java.util.concurrent.TimeUnit

/**
 * Реализация интерфейса [Scheduler][ru.vafeen.universityschedule.domain.scheduler.Scheduler] для планирования повторяющихся задач с использованием WorkManager.
 */
internal class SchedulerImpl(
    private val context: Context, // Контекст приложения для инициализации WorkManager.
    private val dtConverter: DateTimeConverter // Конвертер для обработки даты и времени.
) : Scheduler {

    // Экземпляр WorkManager для управления задачами.
    private val workManager = WorkManager.getInstance(context)

    /**
     * Планирует повторяющуюся задачу для напоминания.
     *
     * @param reminder Напоминание с данными о времени и длительности.
     */
    override fun scheduleRepeatingJob(reminder: Reminder) {
        // Создаем объект данных, передаваемых в Worker.
        val data = Data.Builder()
            .putInt(SchedulerExtra.ID_OF_REMINDER, reminder.idOfReminder) // ID напоминания.
            .build()

        // Вычисляем задержку перед первым запуском задачи.
        val initialDelay = calculateInitialDelay(reminder)

        // Настраиваем ограничения для задачи, чтобы она выполнялась в любых условиях.
        val constraints = androidx.work.Constraints.Builder()
            .setRequiresBatteryNotLow(false) // Игнорировать низкий заряд батареи.
            .setRequiresDeviceIdle(false) // Игнорировать состояние покоя устройства.
            .setRequiredNetworkType(androidx.work.NetworkType.NOT_REQUIRED) // Не требовать сети.
            .build()

        // Создаем запрос на повторяющуюся задачу.
        val repeatingWorkRequest = PeriodicWorkRequest.Builder(
            ReminderWorker::class.java, // Класс Worker, который будет выполнять задачу.
            reminder.duration.duration.milliSeconds, // Интервал повторения задачи.
            TimeUnit.MILLISECONDS
        )
            .setConstraints(constraints) // Устанавливаем ограничения.
            .setInitialDelay(
                initialDelay,
                TimeUnit.MILLISECONDS
            ) // Устанавливаем задержку перед первым запуском.
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
     * Отменяет задачу, связанную с заданным напоминанием.
     *
     * @param reminder Напоминание, для которого нужно отменить задачу.
     */
    override fun cancelJob(reminder: Reminder) {
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
        val reminderTime =
            dtConverter.convertAB(reminder.dt) // Время напоминания, преобразованное в миллисекунды.
        // Если время напоминания позже текущего времени, вычисляем разницу, иначе возвращаем 0.
        return if (reminderTime > now) reminderTime - now else 0L
    }

}