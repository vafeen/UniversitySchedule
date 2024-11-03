package ru.vafeen.universityschedule.domain.notifications

import android.app.Notification
import android.content.Intent

interface NotificationBuilder {
    fun createNotificationAbout15MinutesBeforeLesson(
        title: String = "title",
        text: String = "Hello world!",
        intent: Intent,
    ): Notification

    fun createNotificationAfterBeginningLessonForBeCheckedAtThisLesson(
        title: String = "title",
        text: String = "Hello world!",
        intent: Intent,
    ): Notification

    fun createNotificationReminderRecovery(
        title: String = "title",
        text: String = "Hello world!",
        intent: Intent,
    ): Notification
}