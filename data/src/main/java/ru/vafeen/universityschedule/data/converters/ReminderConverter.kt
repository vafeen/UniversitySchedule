package ru.vafeen.universityschedule.data.converters

import ru.vafeen.universityschedule.data.database.entity.ReminderEntity
import ru.vafeen.universityschedule.domain.converter.BaseConverter
import ru.vafeen.universityschedule.domain.models.Reminder

/**
 * Конвертер для преобразования [ReminderEntity] в [Reminder] и обратно.
 *
 * Используется для преобразования данных между сущностью базы данных и моделью доменного уровня.
 */
internal class ReminderConverter : BaseConverter<ReminderEntity, Reminder> {

    /**
     * Преобразует [ReminderEntity] в [Reminder].
     *
     * @param a Экземпляр [ReminderEntity], полученный из базы данных.
     * @return Экземпляр [Reminder], используемый на уровне доменной модели.
     */
    override fun convertAB(a: ReminderEntity): Reminder = Reminder(
        id = a.id,
        idOfReminder = a.idOfReminder,
        title = a.title,
        text = a.text,
        dt = a.dt,
        duration = a.duration,
        type = a.type
    )

    /**
     * Преобразует [Reminder] в [ReminderEntity].
     *
     * @param b Экземпляр [Reminder], используемый на уровне доменной модели.
     * @return Экземпляр [ReminderEntity], предназначенный для сохранения в базу данных.
     */
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
