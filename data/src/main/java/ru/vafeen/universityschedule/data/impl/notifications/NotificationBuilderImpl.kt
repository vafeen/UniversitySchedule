package ru.vafeen.universityschedule.data.impl.notifications

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.universityschedule.domain.notifications.NotificationBuilder
import ru.vafeen.universityschedule.domain.notifications.NotificationChannelInfo
import ru.vafeen.universityschedule.resources.R

/**
 * Реализация интерфейса [NotificationBuilder] для создания уведомлений.
 */
internal class NotificationBuilderImpl : NotificationBuilder {

    /**
     * Создает уведомление о начале пары за 15 минут до ее начала.
     *
     * @param title Заголовок уведомления.
     * @param text Текст уведомления.
     * @param intent [Intent], который будет запущен при нажатии на уведомление.
     * @return Сформированное уведомление.
     */
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

    /**
     * Создает уведомление о начале пары о необходимости отметки на паре.
     *
     * @param title Заголовок уведомления.
     * @param text Текст уведомления.
     * @param intent [Intent], который будет запущен при нажатии на уведомление.
     * @return Сформированное уведомление.
     */
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

    /**
     * Создает уведомление о восстановлении напоминания.
     *
     * @param title Заголовок уведомления.
     * @param text Текст уведомления.
     * @param intent [Intent], который будет запущен при нажатии на уведомление.
     * @return Сформированное уведомление.
     */
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