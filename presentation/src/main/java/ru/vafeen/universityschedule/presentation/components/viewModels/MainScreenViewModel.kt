package ru.vafeen.universityschedule.presentation.components.viewModels

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.vafeen.universityschedule.data.database.entity.Lesson
import ru.vafeen.universityschedule.data.database.entity.Reminder
import ru.vafeen.universityschedule.data.network.downloader.Downloader
import ru.vafeen.universityschedule.domain.planner.Scheduler
import ru.vafeen.universityschedule.domain.utils.getSettingsOrCreateIfNull
import ru.vafeen.universityschedule.presentation.MainActivity
import java.time.LocalDate


internal class MainScreenViewModel(
    val databaseRepository: ru.vafeen.universityschedule.data.database.DatabaseRepository,
    val sharedPreferences: SharedPreferences,
    private val scheduler: Scheduler,
    private val downloader: Downloader,
    context: Context,
) : ViewModel() {
    private val intent = Intent(context, MainActivity::class.java)
    var nowIsLesson: Boolean = false
    val pageNumber = 365
    val todayDate: LocalDate = LocalDate.now()

    private val settingsInitial = sharedPreferences.getSettingsOrCreateIfNull()
    private val _settingsFlow = MutableStateFlow(settingsInitial)
    val settingsFlow = _settingsFlow.asStateFlow()


    val isUpdateInProcessFlow = downloader.isUpdateInProcessFlow
    val percentageFlow = downloader.percentageFlow

    private val spListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            Log.d("settings", "updated ")
            viewModelScope.launch(Dispatchers.IO) {
                val settings = sharedPreferences.getSettingsOrCreateIfNull()
                _settingsFlow.emit(settings)
            }
        }


    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(spListener)
    }

    override fun onCleared() {
        super.onCleared()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(spListener)
    }

    suspend fun addReminderAbout15MinutesBeforeLessonAndUpdateLocalDB(
        lesson: Lesson,
        newReminder: Reminder,
    ) {
        val newLesson = lesson.copy(idOfReminderBeforeLesson = newReminder.idOfReminder)
        databaseRepository.insertAllLessons(newLesson)
        databaseRepository.insertAllReminders(newReminder)
        scheduler.planRepeatWork(reminder = newReminder, intent = intent)
    }

    suspend fun removeReminderAbout15MinutesBeforeLessonAndUpdateLocalDB(
        lesson: Lesson,
    ) {
        val newLesson = lesson.copy(idOfReminderBeforeLesson = null)
        databaseRepository.insertAllLessons(newLesson)
        val reminder =
            databaseRepository.getReminderByIdOfReminder(
                idOfReminder = lesson.idOfReminderBeforeLesson ?: -1
            )
        reminder?.let {
            scheduler.cancelWork(reminder = it, intent = intent)
            databaseRepository.deleteAllReminders(it)
        }
    }

    suspend fun addReminderAboutCheckingOnLessonAndUpdateLocalDB(
        lesson: Lesson,
        newReminder: Reminder,
    ) {
        val newLesson = lesson.copy(idOfReminderAfterBeginningLesson = newReminder.idOfReminder)
        databaseRepository.insertAllLessons(newLesson)
        databaseRepository.insertAllReminders(newReminder)
        scheduler.planRepeatWork(reminder = newReminder, intent = intent)
    }

    suspend fun removeReminderAboutCheckingOnLessonAndUpdateLocalDB(
        lesson: Lesson,
    ) {
        val newLesson = lesson.copy(idOfReminderAfterBeginningLesson = null)
        databaseRepository.insertAllLessons(newLesson)
        val reminder =
            databaseRepository.getReminderByIdOfReminder(
                idOfReminder = lesson.idOfReminderAfterBeginningLesson ?: -1
            )
        reminder?.let {
            scheduler.cancelWork(reminder = it, intent = intent)
            databaseRepository.deleteAllReminders(it)
        }
    }
}