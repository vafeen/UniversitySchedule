package ru.vafeen.universityschedule.presentation.features.main_screen

import ru.vafeen.universityschedule.domain.models.Lesson
import ru.vafeen.universityschedule.domain.models.Reminder
import ru.vafeen.universityschedule.domain.models.Settings
import ru.vafeen.universityschedule.presentation.navigation.Screen

sealed class MainScreenEvent {
    class NowIsLessonEvent(val nowIsLesson: Boolean) : MainScreenEvent()
    data object MeowEvent : MainScreenEvent()
    data class SaveSettingsEvent(val saving: (Settings) -> Settings) : MainScreenEvent()
    data class IsFrequencyChangingEvent(val isFrequencyChanging: Boolean) : MainScreenEvent()
    data class UpdateLessonEvent(val lesson: Lesson) : MainScreenEvent()
    data class AddReminderAbout15MinutesBeforeLessonAndUpdateLocalDB(
        val lesson: Lesson,
        val newReminder: Reminder
    ) : MainScreenEvent()

    data class RemoveReminderAbout15MinutesBeforeLessonAndUpdateLocalDB(
        val lesson: Lesson
    ) : MainScreenEvent()

    data class AddReminderAboutCheckingOnLessonAndUpdateLocalDB(
        val lesson: Lesson,
        val newReminder: Reminder
    ) : MainScreenEvent()

    data class RemoveReminderAboutCheckingOnLessonAndUpdateLocalDB(val lesson: Lesson) :
        MainScreenEvent()
}