package ru.vafeen.universityschedule.domain.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import ru.vafeen.universityschedule.domain.notifications.ChannelInfo

/**
 * Расширение для класса [ChannelInfo], позволяющее создать объект [NotificationChannel].
 *
 * Эта функция создает новый канал уведомлений на основе информации, содержащейся в объекте [ChannelInfo].
 *
 * @return Новый экземпляр [NotificationChannel], настроенный с использованием данных из [ChannelInfo].
 */
fun ChannelInfo.createNotificationChannelKClass(): NotificationChannel =
    NotificationChannel(
        notificationChannelID,
        notificationChannelName,
        NotificationManager.IMPORTANCE_HIGH
    )
