package ru.vafeen.universityschedule.domain.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import ru.vafeen.universityschedule.domain.notifications.ChannelInfo


fun ChannelInfo.createNotificationChannelKClass(): NotificationChannel =
    NotificationChannel(
        notificationChannelID,
        notificationChannelName,
        NotificationManager.IMPORTANCE_HIGH
    )