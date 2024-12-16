package ru.vafeen.universityschedule.presentation.utils

import android.content.Context
import ru.vafeen.universityschedule.domain.models.Lesson
import ru.vafeen.universityschedule.domain.models.Reminder
import ru.vafeen.universityschedule.domain.models.model_additions.Frequency
import ru.vafeen.universityschedule.domain.models.model_additions.ReminderType
import ru.vafeen.universityschedule.domain.scheduler.duration.RepeatDuration
import ru.vafeen.universityschedule.resources.R
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * Проверка, является ли текущее время временем проведения занятия.
 *
 * Функция проверяет, находится ли переданное время (datetime) в интервале времени начала и окончания занятия.
 *
 * @param datetime Время, которое проверяется на совпадение с временем занятия.
 * @return True, если текущее время находится в пределах времени занятия, иначе False.
 */
internal fun Lesson.nowIsLesson(datetime: LocalTime): Boolean =
    (datetime.hour * 60 * 60 + datetime.minute * 60 + datetime.second) in
            (startTime.hour * 60 * 60 + startTime.minute * 60)..(endTime.hour * 60 * 60 + endTime.minute * 60)

/**
 * Получение строкового представления времени занятия.
 *
 * Функция возвращает строку, представляющую время начала и окончания занятия в формате "часы:минуты - часы:минуты".
 *
 * @return Строка, представляющая время занятия.
 */
internal fun Lesson.getLessonTimeString(): String =
    this.let { "${it.startTime.toLessonTime()} - ${it.endTime.toLessonTime()}" }

/**
 * Создание напоминания за 15 минут до начала занятия.
 *
 * Функция создает объект напоминания, которое будет уведомлять за 15 минут до начала занятия.
 * Напоминание будет повторяться каждую неделю или через неделю, в зависимости от частоты занятия.
 *
 * @param dt Время, когда должно быть создано напоминание.
 * @param idOfNewReminder Уникальный идентификатор напоминания.
 * @param context Контекст для получения строковых ресурсов.
 * @return Напоминание, которое будет создано.
 */
internal fun Lesson.createReminderBefore15MinutesOfLesson(
    dt: LocalDateTime,
    idOfNewReminder: Int,
    context: Context,
): Reminder = Reminder(
    idOfReminder = idOfNewReminder,
    title = context.getString(R.string.reminder_about_lesson_before_time),
    text = String.format(
        context.getString(
            R.string.reminder_text_lesson_before_time
        ),
        name,
        NotificationAboutLessonsSettings.MINUTES_BEFORE_LESSON_FOR_NOTIFICATION,
    ),
    dt = dt,
    type = ReminderType.BEFORE_LESSON,
    duration = if (frequency == Frequency.Every) RepeatDuration.EVERY_WEEK else RepeatDuration.EVERY_2_WEEKS
)

/**
 * Создание напоминания после начала занятия, чтобы оно было проверено в этом занятии.
 *
 * Функция создает объект напоминания, которое будет уведомлять сразу после начала занятия.
 * Напоминание будет повторяться каждую неделю или через неделю, в зависимости от частоты занятия.
 *
 * @param dt Время, когда должно быть создано напоминание.
 * @param idOfNewReminder Уникальный идентификатор напоминания.
 * @param context Контекст для получения строковых ресурсов.
 * @return Напоминание, которое будет создано.
 */
internal fun Lesson.createReminderAfterStartingLessonForBeCheckedAtThisLesson(
    dt: LocalDateTime,
    idOfNewReminder: Int,
    context: Context,
): Reminder = Reminder(
    idOfReminder = idOfNewReminder,
    title = context.getString(R.string.reminder_about_lesson_after_starting),
    text = String.format(
        context.getString(
            R.string.reminder_text_about_lesson_after_starting
        ),
        name
    ),
    dt = dt,
    type = ReminderType.AFTER_BEGINNING_LESSON,
    duration = if (frequency == Frequency.Every) RepeatDuration.EVERY_WEEK else RepeatDuration.EVERY_2_WEEKS
)
