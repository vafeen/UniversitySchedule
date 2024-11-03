package ru.vafeen.universityschedule.presentation.utils

import android.content.Context
import ru.vafeen.universityschedule.domain.models.Lesson
import ru.vafeen.universityschedule.domain.models.Reminder
import ru.vafeen.universityschedule.domain.models.model_additions.Frequency
import ru.vafeen.universityschedule.domain.models.model_additions.ReminderType
import ru.vafeen.universityschedule.domain.scheduler.duration.RepeatDuration
import ru.vafeen.universityschedule.presentation.R
import java.time.LocalDateTime
import java.time.LocalTime


internal fun Lesson.nowIsLesson(datetime: LocalTime): Boolean =
    (datetime.hour * 60 * 60 + datetime.minute * 60 + datetime.second) in
            (startTime.hour * 60 * 60 + startTime.minute * 60)..(endTime.hour * 60 * 60 + endTime.minute * 60)


internal fun Lesson.getLessonTimeString(): String =
    this.let { "${it.startTime.toLessonTime()} - ${it.endTime.toLessonTime()}" }


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
