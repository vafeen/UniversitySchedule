package ru.vafeen.universityschedule.noui.planner

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import ru.vafeen.universityschedule.database.DTConverters
import ru.vafeen.universityschedule.database.entity.Reminder


class Scheduler(
    private val context: Context,
    private val dtConverters: DTConverters,
) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun planRepeatWork(reminder: Reminder) {
        val intent = Intent(context, NotificationAboutLessonReceiver::class.java)
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

    fun cancelWork(reminder: Reminder) {
        val intent = Intent(context, NotificationAboutLessonReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminder.idOfReminder,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }

}