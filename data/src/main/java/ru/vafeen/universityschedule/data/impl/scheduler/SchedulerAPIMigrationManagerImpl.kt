package ru.vafeen.universityschedule.data.impl.scheduler

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import kotlinx.coroutines.flow.first
import org.koin.java.KoinJavaComponent.getKoin
import ru.vafeen.universityschedule.domain.MainActivityIntentProvider
import ru.vafeen.universityschedule.domain.scheduler.Scheduler
import ru.vafeen.universityschedule.domain.scheduler.SchedulerAPIMigrationManager
import ru.vafeen.universityschedule.domain.usecase.db.GetAsFlowRemindersUseCase

internal class SchedulerAPIMigrationManagerImpl(private val context: Context) :
    SchedulerAPIMigrationManager {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val getAsFlowRemindersUseCase = getKoin().get<GetAsFlowRemindersUseCase>()
    private val mainActivityIntentProvider = getKoin().get<MainActivityIntentProvider>()
    private val scheduler = getKoin().get<Scheduler>()
    override suspend fun migrate() {
        val allReminders = getAsFlowRemindersUseCase.invoke().first()

        allReminders.forEach { reminder ->
            alarmManager.cancel(
                PendingIntent.getBroadcast(
                    context,
                    reminder.idOfReminder,
                    mainActivityIntentProvider.provideIntent(),
                    PendingIntent.FLAG_IMMUTABLE
                )
            )
            scheduler.scheduleRepeatingJob(reminder)
        }
    }

}