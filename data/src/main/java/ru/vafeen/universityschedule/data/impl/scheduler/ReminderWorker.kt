package ru.vafeen.universityschedule.data.impl.scheduler

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.getKoin
import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.universityschedule.domain.MainActivityIntentProvider
import ru.vafeen.universityschedule.domain.models.model_additions.ReminderType
import ru.vafeen.universityschedule.domain.notifications.NotificationBuilder
import ru.vafeen.universityschedule.domain.notifications.NotificationService
import ru.vafeen.universityschedule.domain.scheduler.SchedulerExtra
import ru.vafeen.universityschedule.domain.usecase.db.GetReminderByIdOfReminderUseCase

class ReminderWorker(context: Context, params: WorkerParameters) :
    Worker(context, params) {
    private val notificationService: NotificationService by inject(
        clazz = NotificationService::class.java
    )
    private val notificationBuilder: NotificationBuilder = getKoin().get()
    private val getReminderByIdOfReminderUseCase: GetReminderByIdOfReminderUseCase = getKoin().get()
    private val mainActivityIntentProvider = getKoin().get<MainActivityIntentProvider>()
    private val data = params.inputData
    override fun doWork(): Result = try {
        val idOfReminder = data.getInt(
            SchedulerExtra.ID_OF_REMINDER,
            -1
        )
        CoroutineScope(Dispatchers.IO).launch {
            val reminder = getReminderByIdOfReminderUseCase.use(
                idOfReminder = idOfReminder
            )
            reminder?.let {
                when (reminder.type) {
                    ReminderType.BEFORE_LESSON -> {
                        notificationService.showNotification(
                            notificationBuilder.createNotificationAbout15MinutesBeforeLesson(
                                title = it.title,
                                text = it.text,
                                mainActivityIntentProvider.provideIntent()
                            )
                        )
                    }

                    ReminderType.AFTER_BEGINNING_LESSON -> {
                        notificationService.showNotification(
                            notificationBuilder.createNotificationAfterBeginningLessonForBeCheckedAtThisLesson(
                                title = it.title,
                                text = it.text,
                                mainActivityIntentProvider.provideIntent()
                            )
                        )
                    }
                }
            }
        }
        Result.success()
    } catch (ex: Exception) {
        Result.failure()
    }
}