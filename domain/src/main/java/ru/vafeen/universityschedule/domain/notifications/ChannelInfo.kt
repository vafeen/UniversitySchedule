package ru.vafeen.universityschedule.domain.notifications

interface ChannelInfo {
    val notificationChannelID: String
    val notificationChannelName: String
    val requestCode: Int

}