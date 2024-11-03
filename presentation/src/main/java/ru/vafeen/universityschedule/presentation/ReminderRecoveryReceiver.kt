package ru.vafeen.universityschedule.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.universityschedule.domain.notifications.NotificationBuilder
import ru.vafeen.universityschedule.domain.notifications.NotificationService
import ru.vafeen.universityschedule.domain.usecase.db.GetAsFlowRemindersUseCase
import ru.vafeen.universityschedule.domain.usecase.scheduler.CancelJobUseCase
import ru.vafeen.universityschedule.domain.usecase.scheduler.ScheduleRepeatingJobUseCase

class ReminderRecoveryReceiver : BroadcastReceiver() {
    private val getAsFlowRemindersUseCase: GetAsFlowRemindersUseCase by inject(clazz = GetAsFlowRemindersUseCase::class.java)
    private val scheduleRepeatingJobUseCase: ScheduleRepeatingJobUseCase by inject(clazz = ScheduleRepeatingJobUseCase::class.java)
    private val cancelJobUseCase: CancelJobUseCase by inject(clazz = CancelJobUseCase::class.java)
    private val notificationBuilder: NotificationBuilder by inject(clazz = NotificationBuilder::class.java)
    private val notificationService: NotificationService by inject(
        clazz = NotificationService::class.java
    )

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {


            CoroutineScope(Dispatchers.IO).launch {
                for (reminder in getAsFlowRemindersUseCase.use().first()) {
                    cancelJobUseCase.use(reminder = reminder, intent = intent)
                    scheduleRepeatingJobUseCase.use(reminder = reminder, intent = intent)
                }
            }
            notificationService.showNotification(
                notificationBuilder.createNotificationReminderRecovery(
                    title = context.getString(R.string.reminder_recovery),
                    text = context.getString(R.string.reminders_restored),
                    intent = intent
                )
            )
        }
    }
}