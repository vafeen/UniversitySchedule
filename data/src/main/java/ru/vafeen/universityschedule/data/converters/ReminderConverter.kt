package ru.vafeen.universityschedule.data.converters

import ru.vafeen.universityschedule.data.database.entity.ReminderEntity
import ru.vafeen.universityschedule.domain.converter.BaseConverter
import ru.vafeen.universityschedule.domain.models.Reminder

internal class ReminderConverter : BaseConverter<ReminderEntity, Reminder> {
    override fun convertAB(a: ReminderEntity): Reminder = Reminder(
        id = a.id,
        idOfReminder = a.idOfReminder,
        title = a.title,
        text = a.text,
        dt = a.dt,
        duration = a.duration,
        type = a.type
    )

    override fun convertBA(b: Reminder): ReminderEntity =
        ReminderEntity(
            id = b.id,
            idOfReminder = b.idOfReminder,
            title = b.title,
            text = b.text,
            dt = b.dt,
            duration = b.duration,
            type = b.type
        )
}

