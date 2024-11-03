package ru.vafeen.universityschedule.domain.notifications

import android.app.Notification

interface NotificationService {
    fun showNotification(notification: Notification)
}