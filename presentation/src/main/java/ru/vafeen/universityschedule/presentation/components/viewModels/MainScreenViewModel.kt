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
import ru.vafeen.universityschedule.domain.models.Lesson
import ru.vafeen.universityschedule.domain.models.Reminder
import ru.vafeen.universityschedule.domain.network.service.ApkDownloader
import ru.vafeen.universityschedule.domain.usecase.db.DeleteRemindersUseCase
import ru.vafeen.universityschedule.domain.usecase.db.GetAsFlowLessonsUseCase
import ru.vafeen.universityschedule.domain.usecase.db.GetAsFlowRemindersUseCase
import ru.vafeen.universityschedule.domain.usecase.db.GetReminderByIdOfReminderUseCase
import ru.vafeen.universityschedule.domain.usecase.db.InsertLessonsUseCase
import ru.vafeen.universityschedule.domain.usecase.db.InsertRemindersUseCase
import ru.vafeen.universityschedule.domain.usecase.db.UpdateLessonsUseCase
import ru.vafeen.universityschedule.domain.usecase.scheduler.CancelJobUseCase
import ru.vafeen.universityschedule.domain.usecase.scheduler.ScheduleRepeatingJobUseCase
import ru.vafeen.universityschedule.domain.utils.getSettingsOrCreateIfNull
import ru.vafeen.universityschedule.presentation.NotificationAboutLessonReceiver
import java.time.LocalDate


internal class MainScreenViewModel(
    val sharedPreferences: SharedPreferences,
    apkDownloader: ApkDownloader,
    getAsFlowLessonsUseCase: GetAsFlowLessonsUseCase,
    getAsFlowRemindersUseCase: GetAsFlowRemindersUseCase,
    private val insertLessonsUseCase: InsertLessonsUseCase,
    private val insertRemindersUseCase: InsertRemindersUseCase,
    private val deleteAllReminderUseCase: DeleteRemindersUseCase,
    private val getReminderByIdOfReminderUseCase: GetReminderByIdOfReminderUseCase,
    private val scheduleRepeatingJobUseCase: ScheduleRepeatingJobUseCase,
    private val cancelJobUseCase: CancelJobUseCase,
    private val updateLessonsUseCase: UpdateLessonsUseCase,
    context: Context,
) : ViewModel() {
    private val intentForNotification = Intent(context, NotificationAboutLessonReceiver::class.java)
    var nowIsLesson: Boolean = false
    val pageNumber = 365
    val todayDate: LocalDate = LocalDate.now()

    val lessonsFlow = getAsFlowLessonsUseCase.use().map {
        it.toList()
    }
    val remindersFlow = getAsFlowRemindersUseCase.use()

    private val settingsInitial = sharedPreferences.getSettingsOrCreateIfNull()
    private val _settingsFlow = MutableStateFlow(settingsInitial)
    val settingsFlow = _settingsFlow.asStateFlow()


    val isUpdateInProcessFlow = apkDownloader.isUpdateInProcessFlow
    val percentageFlow = apkDownloader.percentageFlow
    fun updateLesson(lesson: Lesson) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("update", "обновление ${lesson.note}")
            updateLessonsUseCase.use(lesson)
        }
    }
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
        insertLessonsUseCase.use(newLesson)
        insertRemindersUseCase.use(newReminder)
        scheduleRepeatingJobUseCase.use(reminder = newReminder, intent = intentForNotification)
    }

    suspend fun removeReminderAbout15MinutesBeforeLessonAndUpdateLocalDB(
        lesson: Lesson
    ) {
        val newLesson = lesson.copy(idOfReminderBeforeLesson = null)
        insertLessonsUseCase.use(newLesson)
        val reminder = getReminderByIdOfReminderUseCase.use(
            idOfReminder = lesson.idOfReminderBeforeLesson ?: -1
        )
        reminder?.let {
            cancelJobUseCase.use(reminder = it, intent = intentForNotification)
            deleteAllReminderUseCase.use(it)
        }
    }

    suspend fun addReminderAboutCheckingOnLessonAndUpdateLocalDB(
        lesson: Lesson, newReminder: Reminder
    ) {
        insertLessonsUseCase.use(lesson.copy(idOfReminderAfterBeginningLesson = newReminder.idOfReminder))

        insertRemindersUseCase.use(newReminder)
        scheduleRepeatingJobUseCase.use(reminder = newReminder, intent = intentForNotification)
    }

    suspend fun removeReminderAboutCheckingOnLessonAndUpdateLocalDB(
        lesson: Lesson,
    ) {
        val newLesson = lesson.copy(idOfReminderAfterBeginningLesson = null)
        insertLessonsUseCase.use(newLesson)
        val reminder = getReminderByIdOfReminderUseCase.use(
            idOfReminder = lesson.idOfReminderAfterBeginningLesson ?: -1
        )
        reminder?.let {
            cancelJobUseCase.use(reminder = it, intent = intentForNotification)
            deleteAllReminderUseCase.use(it)
        }
    }
}