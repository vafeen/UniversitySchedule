package ru.vafeen.universityschedule.ui.components.viewModels

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.vafeen.universityschedule.R
import ru.vafeen.universityschedule.database.DatabaseRepository
import ru.vafeen.universityschedule.database.entity.Lesson
import ru.vafeen.universityschedule.database.entity.Reminder
import ru.vafeen.universityschedule.noui.lesson_additions.Frequency
import ru.vafeen.universityschedule.noui.planner.Scheduler
import ru.vafeen.universityschedule.ui.components.Settings
import ru.vafeen.universityschedule.utils.changeFrequencyIfDefinedInSettings
import ru.vafeen.universityschedule.utils.generateID
import ru.vafeen.universityschedule.utils.getFrequencyByLocalDate
import ru.vafeen.universityschedule.utils.getSettingsOrCreateIfNull
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


class MainScreenViewModel(
    val databaseRepository: DatabaseRepository,
    val sharedPreferences: SharedPreferences,
    private val scheduler: Scheduler,
    context: Context,
) : ViewModel() {
    val ruDaysOfWeek =
        context.let {
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
    val spListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            Log.d("settings", "listener main")
            _settings.value = sharedPreferences.getSettingsOrCreateIfNull()
        }

    val todayDate: LocalDate = LocalDate.now()
    var weekOfYear: Frequency = todayDate.getFrequencyByLocalDate()
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
    val minutesBeforeLessonForNotification = 15L
    val pageNumber = 365
    suspend fun addOrRemoveReminderAndUpdateLocalDatabase(
        lesson: Lesson,
        ld: LocalDate,
        lt: LocalTime,
    ) {
        if (lesson.idOfReminder == null) {
            val reminderIDs = databaseRepository.getAllRemindersAsFlow().first().map {
                it.idOfReminder
            }
            val newDT = LocalDateTime.of(ld, lt)
            val newReminder = Reminder(
                idOfReminder = reminderIDs.generateID(),
                title = "Напоминание о паре!",
                text = "До пары ${lesson.name} $minutesBeforeLessonForNotification минут!",
                dt = newDT
            )
            val newLesson = lesson.copy(idOfReminder = newReminder.idOfReminder)
            databaseRepository.insertAllLessons(newLesson)
            databaseRepository.insertAllReminders(newReminder)
            scheduler.planOneTimeWork(reminder = newReminder)
        } else {
            val newLesson = lesson.copy(idOfReminder = null)
            databaseRepository.insertAllLessons(newLesson)
            val reminder =
                databaseRepository.getReminderByIdOfReminder(idOfReminder = lesson.idOfReminder)
            reminder?.let {
                scheduler.cancelWork(it)
                databaseRepository.deleteAllReminders(it)
            }
        }
    }
}