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

class ReminderRecoveryReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            val databaseRepository: DatabaseRepository by inject(
                clazz = DatabaseRepository::class.java
            )
            val scheduler: Scheduler by inject(
                clazz = Scheduler::class.java
            )
            CoroutineScope(Dispatchers.IO).launch {
                for (reminder in databaseRepository.getAllRemindersAsFlow().first()) {
                    scheduler.cancelWork(reminder = reminder)
                    scheduler.planOneTimeWork(reminder = reminder)
                }
            }
        }
    }

}