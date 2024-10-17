package ru.vafeen.universityschedule.presentation.components.viewModels

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.vafeen.universityschedule.data.R
import ru.vafeen.universityschedule.data.database.entity.Lesson
import ru.vafeen.universityschedule.data.database.entity.Reminder
import ru.vafeen.universityschedule.data.utils.getFrequencyByLocalDate
import ru.vafeen.universityschedule.domain.Settings
import ru.vafeen.universityschedule.domain.planner.Scheduler
import ru.vafeen.universityschedule.domain.utils.changeFrequencyIfDefinedInSettings
import ru.vafeen.universityschedule.domain.utils.getSettingsOrCreateIfNull
import ru.vafeen.universityschedule.presentation.MainActivity
import java.time.LocalDate


internal class MainScreenViewModel(
    val databaseRepository: ru.vafeen.universityschedule.data.database.DatabaseRepository,
    val sharedPreferences: SharedPreferences,
    private val scheduler: Scheduler,
    context: Context,
) : ViewModel() {
    val intent = Intent(context, MainActivity::class.java)
    val ruDaysOfWeek = context.let {
        listOf(
            it.getString(R.string.monday),
            it.getString(R.string.tuesday),
            it.getString(R.string.wednesday),
            it.getString(R.string.thursday),
            it.getString(R.string.friday),
            it.getString(R.string.satudray),
            it.getString(R.string.sunday)
        )
    }

    private val _settings =
        MutableStateFlow<Settings>(sharedPreferences.getSettingsOrCreateIfNull())
    val settings = _settings.asStateFlow()
    private val spListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            _settings.value = sharedPreferences.getSettingsOrCreateIfNull()
        }

    val todayDate: LocalDate = LocalDate.now()
    var weekOfYear: ru.vafeen.universityschedule.data.database.lesson_additions.Frequency =
        todayDate.getFrequencyByLocalDate()
            .changeFrequencyIfDefinedInSettings(settings = settings.value)

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(spListener)
        viewModelScope.launch(Dispatchers.IO) {
            settings.collect {
                weekOfYear = todayDate.getFrequencyByLocalDate()
                    .changeFrequencyIfDefinedInSettings(settings = it)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(spListener)
    }


    var nowIsLesson: Boolean = false

    val pageNumber = 365
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