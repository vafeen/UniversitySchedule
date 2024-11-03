package ru.vafeen.universityschedule.data.impl.notifications

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.universityschedule.resources.R
import ru.vafeen.universityschedule.domain.notifications.NotificationBuilder
import ru.vafeen.universityschedule.domain.notifications.NotificationChannelInfo

internal class NotificationBuilderImpl : NotificationBuilder {
    override fun createNotificationAbout15MinutesBeforeLesson(
        title: String,
        text: String,
        intent: Intent,
    ): Notification {
        val context: Context by inject(
            clazz = Context::class.java
        )
        val pendingIntent = PendingIntent.getActivity(
            context,
            NotificationChannelInfo.About15MinutesBeforeLesson.requestCode,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(
            context,
            NotificationChannelInfo.About15MinutesBeforeLesson.notificationChannelID
        )
            .setSmallIcon(R.drawable.message)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()
    }

    override fun createNotificationAfterBeginningLessonForBeCheckedAtThisLesson(
        title: String,
        text: String,
        intent: Intent,
    ): Notification {
        val context: Context by inject(
            clazz = Context::class.java
        )
        val pendingIntent = PendingIntent.getActivity(
            context,
            NotificationChannelInfo.AfterStartingLesson.requestCode,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(
            context,
            NotificationChannelInfo.AfterStartingLesson.notificationChannelID
        )
            .setSmallIcon(R.drawable.notification_about_checking)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()
    }

    override fun createNotificationReminderRecovery(
        title: String,
        text: String,
        intent: Intent,
    ): Notification {
        val context: Context by inject(
            clazz = Context::class.java
        )
        val pendingIntent = PendingIntent.getActivity(
            context,
            NotificationChannelInfo.ReminderRecovery.requestCode,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        return NotificationCompat.Builder(
            context,
            NotificationChannelInfo.ReminderRecovery.notificationChannelID
        )
            .setSmallIcon(R.drawable.reminder)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()
    }
}