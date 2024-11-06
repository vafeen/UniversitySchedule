package ru.vafeen.universityschedule.domain.notifications

object NotificationChannelInfo {
    object About15MinutesBeforeLesson : ChannelInfo {
        override val notificationChannelID = "15 minutes before"
        override val notificationChannelName = "15 minutes before"
        override val requestCode = 200
    }

    object AfterStartingLesson : ChannelInfo {
        override val notificationChannelID = "After starting"
        override val notificationChannelName = "After starting"
        override val requestCode = 201
    }

    object ReminderRecovery : ChannelInfo {
        override val notificationChannelID: String = "Reminder recovery"
        override val notificationChannelName: String = "Reminder recovery"
        override val requestCode: Int = 202
    }
}