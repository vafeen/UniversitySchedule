package ru.vafeen.universityschedule.data.impl.notifications

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import ru.vafeen.universityschedule.domain.notifications.NotificationService
import kotlin.random.Random


internal class NotificationServiceImpl(
    private val context: Context,
) : NotificationService {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    override fun showNotification(notification: Notification) {
        notificationManager.notify(Random.nextInt(), notification)
    }
}