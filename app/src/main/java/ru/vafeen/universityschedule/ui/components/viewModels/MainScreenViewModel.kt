package ru.vafeen.universityschedule.ui.components.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.vafeen.universityschedule.R
import ru.vafeen.universityschedule.database.DatabaseRepository
import ru.vafeen.universityschedule.database.entity.Lesson
import ru.vafeen.universityschedule.database.entity.Reminder
import ru.vafeen.universityschedule.network.service.GSheetsService
import ru.vafeen.universityschedule.noui.planner.Scheduler
import ru.vafeen.universityschedule.utils.GSheetsServiceRequestStatus
import ru.vafeen.universityschedule.noui.shared_preferences.SharedPreferences
import ru.vafeen.universityschedule.utils.changeFrequencyIfDefinedInSettings
import ru.vafeen.universityschedule.utils.cleverUpdatingLessons
import ru.vafeen.universityschedule.utils.createGSheetsService
import ru.vafeen.universityschedule.utils.generateID
import ru.vafeen.universityschedule.utils.getFrequencyByLocalDate
import ru.vafeen.universityschedule.utils.getLessonsListFromGSheetsTable
import ru.vafeen.universityschedule.utils.getSettingsOrCreateIfNull
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


class MainScreenViewModel(
    val databaseRepository: DatabaseRepository,
    val sharedPreferences: SharedPreferences,
    private val scheduler: Scheduler,
    context: Context
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
    var settings = sharedPreferences.getSettingsOrCreateIfNull()
    val todayDate: LocalDate = LocalDate.now()
    val daysOfWeek = DayOfWeek.entries.toList()
    val weekOfYear = todayDate.getFrequencyByLocalDate()
        .changeFrequencyIfDefinedInSettings(settings = settings)
    var nowIsLesson: Boolean = false
    private var gSheetsService: GSheetsService? =
        settings.link?.let { createGSheetsService(link = it) }
    val minutesBeforeLessonForNotification = 15L
    val pageNumber = 365

    fun updateLocalDatabase(updateUICallback: (GSheetsServiceRequestStatus) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                updateUICallback(
                    if (settings.link?.isNotEmpty() == true) GSheetsServiceRequestStatus.Waiting
                    else GSheetsServiceRequestStatus.NoLink
                )
            }
            if (settings.link?.isNotEmpty() == true)
                try {
                    gSheetsService?.getLessonsListFromGSheetsTable()
                        ?.let {
                            cleverUpdatingLessons(newLessons = it)
                            withContext(Dispatchers.Main) {
                                updateUICallback(GSheetsServiceRequestStatus.Success)
                            }
                        }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        updateUICallback(GSheetsServiceRequestStatus.NetworkError)
                    }
                }
        }
    }

    suspend fun addOrRemoveReminderAndUpdateLocalDatabase(
        lesson: Lesson,
        ld: LocalDate,
        lt: LocalTime
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