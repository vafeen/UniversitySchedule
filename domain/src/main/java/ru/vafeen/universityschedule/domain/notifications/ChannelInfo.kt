package ru.vafeen.universityschedule.domain.notifications

import android.app.NotificationChannel
import android.app.NotificationManager

interface ChannelInfo {
    val notificationChannelID: String
    val notificationChannelName: String
    val requestCode: Int

}

fun ChannelInfo.createNotificationChannelKClass(): NotificationChannel =
    NotificationChannel(
        notificationChannelID,
        notificationChannelName,
        NotificationManager.IMPORTANCE_HIGH
    )