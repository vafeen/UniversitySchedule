package ru.vafeen.universityschedule.domain.utils

import ru.vafeen.universityschedule.domain.models.Reminder

/**
 * Генерирует уникальный идентификатор для задачи напоминания.
 *
 * Эта функция возвращает уникальный идентификатор задачи напоминания в виде строки.
 *
 * @return Уникальный идентификатор задачи, представленный в виде строки.
 */
fun Reminder.getUniqueReminderWorkID(): String = idOfReminder.toString()
