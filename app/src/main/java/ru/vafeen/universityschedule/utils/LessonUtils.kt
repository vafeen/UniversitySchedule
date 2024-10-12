package ru.vafeen.universityschedule.utils

import android.content.Context
import ru.vafeen.universityschedule.R
import ru.vafeen.universityschedule.database.ReminderType
import ru.vafeen.universityschedule.database.entity.Lesson
import ru.vafeen.universityschedule.database.entity.Reminder
import ru.vafeen.universityschedule.noui.duration.RepeatDuration
import ru.vafeen.universityschedule.noui.lesson_additions.Frequency
import java.time.LocalDateTime
import java.time.LocalTime

fun Lesson.nowIsLesson(datetime: LocalTime): Boolean =
    (datetime.hour * 60 * 60 + datetime.minute * 60 + datetime.second) in
            (startTime.hour * 60 * 60 + startTime.minute * 60)..(endTime.hour * 60 * 60 + endTime.minute * 60)


fun Lesson.getLessonTimeString(): String =
    this.let { "${it.startTime.toLessonTime()} - ${it.endTime.toLessonTime()}" }


fun Lesson.createReminderBefore15MinutesOfLesson(
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
        NotificationAboutLessonsSettings.minutesBeforeLessonForNotification,
    ),
    dt = dt,
    type = ReminderType.BEFORE_LESSON,
    duration = if (frequency == Frequency.Every) RepeatDuration.EVERY_WEEK else RepeatDuration.EVERY_2_WEEKS
)


fun Lesson.createReminderAfterStartingLessonForBeCheckedAtThisLesson(
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
