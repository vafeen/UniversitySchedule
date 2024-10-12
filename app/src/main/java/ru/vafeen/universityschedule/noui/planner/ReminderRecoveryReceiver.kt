package ru.vafeen.universityschedule.noui.planner

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.universityschedule.R
import ru.vafeen.universityschedule.database.DatabaseRepository
import ru.vafeen.universityschedule.noui.notifications.NotificationService

class ReminderRecoveryReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            val databaseRepository: DatabaseRepository by inject(
                clazz = DatabaseRepository::class.java
            )
            val context: Context by inject(
                clazz = Context::class.java
            )
            val scheduler: Scheduler by inject(
                clazz = Scheduler::class.java
            )
            val notificationService: NotificationService by inject(
                clazz = NotificationService::class.java
            )
            CoroutineScope(Dispatchers.IO).launch {
                for (reminder in databaseRepository.getAllRemindersAsFlow().first()) {
                    scheduler.cancelWork(reminder = reminder)
                    scheduler.planRepeatWork(reminder = reminder)
                }
            }
            notificationService.showNotification(
                NotificationService.createNotificationReminderRecovery(
                    title = context.getString(R.string.reminder_recovery),
                    text = context.getString(R.string.reminders_restored)
                )
            )
        }
    }

}