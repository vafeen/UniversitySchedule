package ru.vafeen.universityschedule.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.universityschedule.domain.notifications.NotificationBuilder
import ru.vafeen.universityschedule.domain.notifications.NotificationService
import ru.vafeen.universityschedule.domain.usecase.db.GetReminderByIdOfReminderUseCase

class NotificationAboutLessonReceiver : BroadcastReceiver() {
    private val notificationService: NotificationService by inject(
        clazz = NotificationService::class.java
    )
    private val notificationBuilder: NotificationBuilder by inject(clazz = NotificationBuilder::class.java)
    private val getReminderByIdOfReminderUseCase: GetReminderByIdOfReminderUseCase by inject(clazz = GetReminderByIdOfReminderUseCase::class.java)
    override fun onReceive(context: Context, intent: Intent) {

    }
}