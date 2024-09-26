package ru.vafeen.universityschedule.noui.planner

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import ru.vafeen.universityschedule.database.entity.Reminder


class Scheduler(private val context: Context) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val intent = Intent(context, AlarmReceiver::class.java)

    @SuppressLint("ScheduleExactAlarm")
    fun planOneTimeWork(reminder: Reminder) {
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminder.hashCode(),
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        // Установите время срабатывания будильника
        val triggerTime = System.currentTimeMillis() + 60000 // Через 1 минуту

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
    }

    fun planRepeatWork(reminder: Reminder) {

    }

    fun cancelWork(reminder: Reminder) {

    }

}