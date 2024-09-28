package ru.vafeen.universityschedule.noui.planner

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.universityschedule.database.DatabaseRepository
import ru.vafeen.universityschedule.noui.notifications.NotificationService
import ru.vafeen.universityschedule.noui.shared_preferences.SharedPreferences
import ru.vafeen.universityschedule.utils.getSettingsOrCreateIfNull
import ru.vafeen.universityschedule.utils.save

class ReminderRecoveryReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            val sharedPreferences: SharedPreferences by inject(
                clazz = SharedPreferences::class.java
            )
            sharedPreferences.getSettingsOrCreateIfNull().copy(link = null).save(sharedPreferences)
            val databaseRepository: DatabaseRepository by inject(
                clazz = DatabaseRepository::class.java
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
                    scheduler.planOneTimeWork(reminder = reminder)
                }
            }
            notificationService.showNotification(
                "Восстановление будильников",
                "Будильники восстановлены!"
            )
        }
    }

}