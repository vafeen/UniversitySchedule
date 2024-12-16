package ru.vafeen.universityschedule.presentation.components.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.vafeen.universityschedule.domain.models.Lesson
import ru.vafeen.universityschedule.domain.models.Reminder
import ru.vafeen.universityschedule.domain.models.Settings
import ru.vafeen.universityschedule.domain.network.service.SettingsManager
import ru.vafeen.universityschedule.domain.usecase.CatMeowUseCase
import ru.vafeen.universityschedule.domain.usecase.db.DeleteRemindersUseCase
import ru.vafeen.universityschedule.domain.usecase.db.GetAsFlowLessonsUseCase
import ru.vafeen.universityschedule.domain.usecase.db.GetAsFlowRemindersUseCase
import ru.vafeen.universityschedule.domain.usecase.db.GetReminderByIdOfReminderUseCase
import ru.vafeen.universityschedule.domain.usecase.db.InsertLessonsUseCase
import ru.vafeen.universityschedule.domain.usecase.db.InsertRemindersUseCase
import ru.vafeen.universityschedule.domain.usecase.db.UpdateLessonsUseCase
import ru.vafeen.universityschedule.domain.usecase.scheduler.CancelJobUseCase
import ru.vafeen.universityschedule.domain.usecase.scheduler.ScheduleRepeatingJobUseCase
import java.time.LocalDate

/**
 * ViewModel для главного экрана приложения, который управляет расписанием пар,
 * напоминаниями и настройками. Обрабатывает добавление, удаление, обновление пар и напоминаний,
 * а также управление миграцией данных и задачами планировщика.
 *
 * @param getAsFlowLessonsUseCase UseCase для получения пар как потока данных.
 * @param getAsFlowRemindersUseCase UseCase для получения напоминаний как потока данных.
 * @param insertLessonsUseCase UseCase для добавления новых пар.
 * @param insertRemindersUseCase UseCase для добавления новых напоминаний.
 * @param deleteAllReminderUseCase UseCase для удаления всех напоминаний.
 * @param getReminderByIdOfReminderUseCase UseCase для получения напоминания по ID.
 * @param scheduleRepeatingJobUseCase UseCase для планирования повторяющихся задач.
 * @param catMeowUseCase UseCase для вызова "мяу" .
 * @param cancelJobUseCase UseCase для отмены задач.
 * @param updateLessonsUseCase UseCase для обновления информации о парах.
 * @param settingsManager Менеджер для работы с настройками приложения.
 */
internal class MainScreenViewModel(
    getAsFlowLessonsUseCase: GetAsFlowLessonsUseCase,
    getAsFlowRemindersUseCase: GetAsFlowRemindersUseCase,
    private val insertLessonsUseCase: InsertLessonsUseCase,
    private val insertRemindersUseCase: InsertRemindersUseCase,
    private val deleteAllReminderUseCase: DeleteRemindersUseCase,
    private val getReminderByIdOfReminderUseCase: GetReminderByIdOfReminderUseCase,
    private val scheduleRepeatingJobUseCase: ScheduleRepeatingJobUseCase,
    private val catMeowUseCase: CatMeowUseCase,
    private val cancelJobUseCase: CancelJobUseCase,
    private val updateLessonsUseCase: UpdateLessonsUseCase,
    private val settingsManager: SettingsManager
) : ViewModel() {

    // Переменная для отслеживания текущего состояния пары
    var nowIsLesson: Boolean = false

    // Количество дней для отображения в расписании (например, 365 дней)
    val pageNumber = 365

    // Текущая дата
    val todayDate: LocalDate = LocalDate.now()

    // Поток данных с парами
    val lessonsFlow = getAsFlowLessonsUseCase.invoke().map {
        it.toList()
    }

    // Поток данных с напоминаниями
    val remindersFlow = getAsFlowRemindersUseCase.invoke()

    // Поток с настройками приложения
    val settingsFlow = settingsManager.settingsFlow

    /**
     * Вызов "мяу" на котиках.
     */
    fun meow() {
        catMeowUseCase.invoke()
    }

    /**
     * Функция для обновления данных о паре.
     * @param lesson Пара, данные которой нужно обновить.
     */
    fun updateLesson(lesson: Lesson) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("update", "обновление ${lesson.note}")
            updateLessonsUseCase.invoke(lesson)
        }
    }

    /**
     * Функция для добавления напоминания за 15 минут до начала пары
     * и обновления локальной базы данных.
     * @param lesson Пара, для которой создается напоминание.
     * @param newReminder Новое напоминание.
     */
    suspend fun addReminderAbout15MinutesBeforeLessonAndUpdateLocalDB(
        lesson: Lesson, newReminder: Reminder
    ) {
        val newLesson = lesson.copy(idOfReminderBeforeLesson = newReminder.idOfReminder)
        insertLessonsUseCase.invoke(newLesson)
        insertRemindersUseCase.invoke(newReminder)
        scheduleRepeatingJobUseCase.invoke(reminder = newReminder)
    }

    /**
     * Функция для удаления напоминания о проверке за 15 минут до начала пары
     * и обновления локальной базы данных.
     * @param lesson Пара, для которой удаляется напоминание.
     */
    suspend fun removeReminderAbout15MinutesBeforeLessonAndUpdateLocalDB(
        lesson: Lesson
    ) {
        val newLesson = lesson.copy(idOfReminderBeforeLesson = null)
        insertLessonsUseCase.invoke(newLesson)
        val reminder = getReminderByIdOfReminderUseCase.invoke(
            idOfReminder = lesson.idOfReminderBeforeLesson ?: -1
        )
        reminder?.let {
            cancelJobUseCase.invoke(reminder = it)
            deleteAllReminderUseCase.invoke(it)
        }
    }

    /**
     * Функция для сохранения настроек в SharedPreferences.
     * @param saving Функция, изменяющая настройки.
     */
    fun saveSettingsToSharedPreferences(saving: (Settings) -> Settings) {
        settingsManager.save(saving)
    }

    /**
     * Функция для добавления напоминания об отметке на паре
     * и обновления локальной базы данных.
     * @param lesson Пара, для которой создается напоминание.
     * @param newReminder Новое напоминание.
     */
    suspend fun addReminderAboutCheckingOnLessonAndUpdateLocalDB(
        lesson: Lesson, newReminder: Reminder
    ) {
        insertLessonsUseCase.invoke(lesson.copy(idOfReminderAfterBeginningLesson = newReminder.idOfReminder))

        insertRemindersUseCase.invoke(newReminder)
        scheduleRepeatingJobUseCase.invoke(reminder = newReminder)
    }

    /**
     * Функция для удаления напоминания об отметке на паре
     * и обновления локальной базы данных.
     * @param lesson Пара, для которой удаляется напоминание.
     */
    suspend fun removeReminderAboutCheckingOnLessonAndUpdateLocalDB(
        lesson: Lesson,
    ) {
        val newLesson = lesson.copy(idOfReminderAfterBeginningLesson = null)
        insertLessonsUseCase.invoke(newLesson)
        val reminder = getReminderByIdOfReminderUseCase.invoke(
            idOfReminder = lesson.idOfReminderAfterBeginningLesson ?: -1
        )
        reminder?.let {
            cancelJobUseCase.invoke(reminder = it)
            deleteAllReminderUseCase.invoke(it)
        }
    }
}
