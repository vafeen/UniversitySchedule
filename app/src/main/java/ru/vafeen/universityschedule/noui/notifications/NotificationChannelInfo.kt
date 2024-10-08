package ru.vafeen.universityschedule.noui.notifications

object NotificationChannelInfo {
    object About15MinutesBeforeLesson : ChannelInfo {
        override val NOTIFICATION_CHANNEL_ID = "15 minutes before"
        override val NOTIFICATION_CHANNEL_NAME = "15 minutes before"
        override val REQUEST_CODE = 200
    }

    object AfterStartingLesson : ChannelInfo {
        override val NOTIFICATION_CHANNEL_ID = "After starting"
        override val NOTIFICATION_CHANNEL_NAME = "After starting"
        override val REQUEST_CODE = 201
    }

    object ReminderRecovery : ChannelInfo {
        override val NOTIFICATION_CHANNEL_ID: String = "Reminder recovery"
        override val NOTIFICATION_CHANNEL_NAME: String = "Reminder recovery"
        override val REQUEST_CODE: Int = 202
    }
}