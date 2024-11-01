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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.vafeen.universityschedule.domain.database.models.Lesson
import ru.vafeen.universityschedule.domain.database.models.Reminder
import ru.vafeen.universityschedule.domain.network.downloader.Downloader
import ru.vafeen.universityschedule.domain.planner.usecase.CancelJobUseCase
import ru.vafeen.universityschedule.domain.planner.usecase.ScheduleRepeatingJobUseCase
import ru.vafeen.universityschedule.domain.usecase.db.DeleteRemindersUseCase
import ru.vafeen.universityschedule.domain.usecase.db.GetAsFlowLessonsUseCase
import ru.vafeen.universityschedule.domain.usecase.db.GetAsFlowRemindersUseCase
import ru.vafeen.universityschedule.domain.usecase.db.GetReminderByIdOfReminderUseCase
import ru.vafeen.universityschedule.domain.usecase.db.InsertLessonsUseCase
import ru.vafeen.universityschedule.domain.usecase.db.InsertRemindersUseCase
import ru.vafeen.universityschedule.domain.usecase.network.GetLatestReleaseUseCase
import ru.vafeen.universityschedule.domain.utils.getSettingsOrCreateIfNull
import ru.vafeen.universityschedule.presentation.NotificationAboutLessonReceiver
import java.time.LocalDate


internal class MainScreenViewModel(
    val sharedPreferences: SharedPreferences,
    private val downloader: Downloader,
    private val getAsFlowLessonsUseCase: GetAsFlowLessonsUseCase,
    private val getAsFlowRemindersUseCase: GetAsFlowRemindersUseCase,
    private val insertLessonsUseCase: InsertLessonsUseCase,
    private val insertRemindersUseCase: InsertRemindersUseCase,
    private val deleteAllReminderUseCase: DeleteRemindersUseCase,
    private val getReminderByIdOfReminderUseCase: GetReminderByIdOfReminderUseCase,
    private val scheduleRepeatingJobUseCase: ScheduleRepeatingJobUseCase,
    private val cancelJobUseCase: CancelJobUseCase,
    val getLatestReleaseUseCase: GetLatestReleaseUseCase,
    context: Context,
) : ViewModel() {
    private val intentForNotification = Intent(context, NotificationAboutLessonReceiver::class.java)
    var nowIsLesson: Boolean = false
    val pageNumber = 365
    val todayDate: LocalDate = LocalDate.now()

    val lessonsFlow = getAsFlowLessonsUseCase.invoke().map {
        it.toList()
    }
    val remindersFlow = getAsFlowRemindersUseCase.invoke()

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
        lesson: Lesson, newReminder: Reminder
    ) {
        val newLesson = lesson.copy(idOfReminderBeforeLesson = newReminder.idOfReminder)
        insertLessonsUseCase.invoke(newLesson)
        insertRemindersUseCase.invoke(newReminder)
        scheduleRepeatingJobUseCase.invoke(reminder = newReminder, intent = intentForNotification)
    }

    suspend fun removeReminderAbout15MinutesBeforeLessonAndUpdateLocalDB(
        lesson: Lesson
    ) {
        val newLesson = lesson.copy(idOfReminderBeforeLesson = null)
        insertLessonsUseCase(newLesson)
        val reminder = getReminderByIdOfReminderUseCase(
            idOfReminder = lesson.idOfReminderBeforeLesson ?: -1
        )
        reminder?.let {
            cancelJobUseCase(reminder = it, intent = intentForNotification)
            deleteAllReminderUseCase(it)
        }
    }

    suspend fun addReminderAboutCheckingOnLessonAndUpdateLocalDB(
        lesson: Lesson, newReminder: Reminder
    ) {
        insertLessonsUseCase(lesson.copy(idOfReminderAfterBeginningLesson = newReminder.idOfReminder))

        insertRemindersUseCase(newReminder)
        scheduleRepeatingJobUseCase(reminder = newReminder, intent = intentForNotification)
    }

    suspend fun removeReminderAboutCheckingOnLessonAndUpdateLocalDB(
        lesson: Lesson,
    ) {
        val newLesson = lesson.copy(idOfReminderAfterBeginningLesson = null)
        insertLessonsUseCase(newLesson)
        val reminder = getReminderByIdOfReminderUseCase(
            idOfReminder = lesson.idOfReminderAfterBeginningLesson ?: -1
        )
        reminder?.let {
            cancelJobUseCase(reminder = it, intent = intentForNotification)
            deleteAllReminderUseCase(it)
        }
    }
}