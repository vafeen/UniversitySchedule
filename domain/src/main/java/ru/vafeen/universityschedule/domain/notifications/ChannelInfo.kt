package ru.vafeen.universityschedule.domain.notifications

/**
 * Интерфейс, представляющий информацию о канале уведомлений.
 */
interface ChannelInfo {

    /**
     * Идентификатор канала уведомлений.
     */
    val notificationChannelID: String

    /**
     * Название канала уведомлений.
     */
    val notificationChannelName: String

    /**
     * Код запроса, используемый для идентификации уведомлений.
     */
    val requestCode: Int
}
