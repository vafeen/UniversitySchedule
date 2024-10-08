package ru.vafeen.universityschedule.noui.notifications

import android.app.NotificationChannel
import android.app.NotificationManager

interface ChannelInfo {
    val NOTIFICATION_CHANNEL_ID: String
    val NOTIFICATION_CHANNEL_NAME: String
    val REQUEST_CODE: Int

}

fun ChannelInfo.createNotificationChannelKClass(): NotificationChannel =
    NotificationChannel(
        NOTIFICATION_CHANNEL_ID,
        NOTIFICATION_CHANNEL_NAME,
        NotificationManager.IMPORTANCE_HIGH
    )