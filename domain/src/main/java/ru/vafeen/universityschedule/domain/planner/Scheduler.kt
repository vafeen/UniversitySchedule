package ru.vafeen.universityschedule.domain.planner

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import ru.vafeen.universityschedule.data.database.DTConverters
import ru.vafeen.universityschedule.domain.database.models.Reminder


internal class Scheduler(
    private val context: Context,
    private val dtConverters: DTConverters,
) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun scheduleRepeatingJob(
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
            dtConverters.localDateTimeToLongMilliSeconds(reminder.dt),
            reminder.duration.duration.milliSeconds,
            pendingIntent
        )
    }

    fun cancelJob(
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