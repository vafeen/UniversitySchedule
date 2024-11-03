package ru.vafeen.universityschedule.domain.converters

import ru.vafeen.universityschedule.domain.converters.base.BaseConverter
import ru.vafeen.universityschedule.domain.database.entity.ReminderEntity
import ru.vafeen.universityschedule.domain.models.Reminder
import ru.vafeen.universityschedule.domain.models.model_additions.ReminderType
import ru.vafeen.universityschedule.domain.scheduler.duration.RepeatDuration

class ReminderConverter : BaseConverter<ReminderEntity, Reminder> {
    override fun convertAB(a: ReminderEntity): Reminder = Reminder(
        id = a.id,
        idOfReminder = a.idOfReminder,
        title = a.title,
        text = a.text,
        dt = a.dt,
        duration = RepeatDuration.valueOf(a.duration),
        type = ReminderType.valueOf(a.type)
    )

    override fun convertBA(b: Reminder): ReminderEntity = ReminderEntity(
        id = b.id,
        idOfReminder = b.idOfReminder,
        title = b.title,
        text = b.text,
        dt = b.dt,
        duration = b.duration.toString(),
        type = b.type.toString()
    )
}

