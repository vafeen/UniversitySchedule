package ru.vafeen.universityschedule.noui.notifications

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.universityschedule.R
import ru.vafeen.universityschedule.application_main.MainActivity
import kotlin.random.Random


class NotificationService(
    private val context: Context,
) {
    companion object {
        fun createNotificationAbout15MinutesBeforeLesson(
            title: String = "title",
            text: String = "Hello world!",
        ): Notification {
            val context: Context by inject(
                clazz = Context::class.java
            )
            val pendingIntent = PendingIntent.getActivity(
                context,
                NotificationChannelInfo.About15MinutesBeforeLesson.REQUEST_CODE,
                Intent(context, MainActivity::class.java),
                PendingIntent.FLAG_IMMUTABLE
            )

            return NotificationCompat.Builder(
                context,
                NotificationChannelInfo.About15MinutesBeforeLesson.NOTIFICATION_CHANNEL_ID
            )
                .setSmallIcon(R.drawable.message)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .build()
        }

        fun createNotificationAfterBeginningLessonForBeCheckedAtThisLesson(
            title: String = "title",
            text: String = "Hello world!",
        ): Notification {
            val context: Context by inject(
                clazz = Context::class.java
            )
            val pendingIntent = PendingIntent.getActivity(
                context,
                NotificationChannelInfo.AfterStartingLesson.REQUEST_CODE,
                Intent(context, MainActivity::class.java),
                PendingIntent.FLAG_IMMUTABLE
            )

            return NotificationCompat.Builder(
                context,
                NotificationChannelInfo.AfterStartingLesson.NOTIFICATION_CHANNEL_ID
            )
                .setSmallIcon(R.drawable.notification_about_checking)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .build()
        }

        fun createNotificationReminderRecovery(
            title: String = "title",
            text: String = "Hello world!",
        ): Notification {
            val context: Context by inject(
                clazz = Context::class.java
            )
            val pendingIntent = PendingIntent.getActivity(
                context,
                NotificationChannelInfo.ReminderRecovery.REQUEST_CODE,
                Intent(context, MainActivity::class.java),
                PendingIntent.FLAG_IMMUTABLE
            )
            return NotificationCompat.Builder(
                context,
                NotificationChannelInfo.ReminderRecovery.NOTIFICATION_CHANNEL_ID
            )
                .setSmallIcon(R.drawable.reminder)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .build()
        }
    }

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    fun showNotification(notification: Notification) {
        notificationManager.notify(Random.nextInt(), notification)
    }
}