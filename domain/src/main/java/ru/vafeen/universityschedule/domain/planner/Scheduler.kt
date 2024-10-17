package ru.vafeen.universityschedule.domain.planner

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import ru.vafeen.universityschedule.data.database.DTConverters
import ru.vafeen.universityschedule.data.database.entity.Reminder


class Scheduler(
    private val context: Context,
    private val dtConverters: DTConverters,
) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun planRepeatWork(
        reminder: Reminder,
        intent: Intent
    ) {
//        val intent = Intent(
//            context,
//            ru.vafeen.universityschedule.presentation.NotificationAboutLessonReceiver::class.java
//        )
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

    fun cancelWork(
        reminder: Reminder,
        intent: Intent
    ) {
//        val intent = Intent(
//            context,
//            ru.vafeen.universityschedule.presentation.NotificationAboutLessonReceiver::class.java
//        )
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminder.idOfReminder,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }

}