package ru.vafeen.universityschedule.domain.utils

import ru.vafeen.universityschedule.domain.models.Reminder


/**
 * Генерирует уникальный идентификатор для задачи напоминания.
 *
 * @return Уникальный идентификатор задачи.
 */
fun Reminder.getUniqueReminderWorkID(): String = idOfReminder.toString()