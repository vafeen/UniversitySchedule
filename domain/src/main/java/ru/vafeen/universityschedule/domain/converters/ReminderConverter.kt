package ru.vafeen.universityschedule.domain.converters

import ru.vafeen.universityschedule.data.database.entity.ReminderEntity
import ru.vafeen.universityschedule.domain.converters.base.BaseConverter
import ru.vafeen.universityschedule.domain.database.models.Reminder
import ru.vafeen.universityschedule.domain.model_additions.ReminderType
import ru.vafeen.universityschedule.domain.planner.duration.RepeatDuration

internal class ReminderConverter : BaseConverter<ReminderEntity, Reminder> {

    override fun convertEntityDTO(e: ReminderEntity): Reminder = Reminder(
        id = e.id,
        idOfReminder = e.idOfReminder,
        title = e.title,
        text = e.text,
        dt = e.dt,
        duration = RepeatDuration.valueOf(e.duration),
        type = ReminderType.valueOf(e.type)
    )

    override fun convertDTOEntity(d: Reminder): ReminderEntity = ReminderEntity(
        id = d.id,
        idOfReminder = d.idOfReminder,
        title = d.title,
        text = d.text,
        dt = d.dt,
        duration = d.duration.toString(),
        type = d.type.toString()
    )
}
