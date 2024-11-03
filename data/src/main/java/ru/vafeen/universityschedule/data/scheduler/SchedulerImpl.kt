package ru.vafeen.universityschedule.data.scheduler

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import ru.vafeen.universityschedule.domain.converters.DateTimeConverter
import ru.vafeen.universityschedule.domain.models.Reminder
import ru.vafeen.universityschedule.domain.scheduler.Scheduler
import ru.vafeen.universityschedule.domain.scheduler.SchedulerExtra


internal class SchedulerImpl(
    private val context: Context,
    private val dtConverter: DateTimeConverter,
) : Scheduler {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun scheduleRepeatingJob(
        reminder: Reminder,
        intent: Intent
    ) {
        intent.apply {
            putExtra(SchedulerExtra.ID_OF_REMINDER, reminder.idOfReminder)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminder.idOfReminder,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            dtConverter.convertAB(reminder.dt),
            reminder.duration.duration.milliSeconds,
            pendingIntent
        )
    }

    override fun cancelJob(
        reminder: Reminder,
        intent: Intent
    ) {
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminder.idOfReminder,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }

}