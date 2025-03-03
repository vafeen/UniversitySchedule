package ru.vafeen.universityschedule.presentation.features.main_screen

import ru.vafeen.universityschedule.domain.models.Lesson
import ru.vafeen.universityschedule.domain.models.Reminder
import ru.vafeen.universityschedule.domain.models.Settings
import java.time.LocalDate

data class MainScreenState(
    // Переменная для отслеживания текущего состояния пары
    val nowIsLesson: Boolean = false,
    // Количество дней для отображения в расписании (например, 365 дней)
    val pageNumber: Int = 365,
    // Текущая дата
    val todayDate: LocalDate = LocalDate.now(),
    // Список пар
    val lessons: List<Lesson> = listOf(),
    // Список напоминаний
    val reminders: List<Reminder> = listOf(),
    // настройки приложения
    val settings: Settings = Settings(),
    // Состояние изменения частоты
    val isFrequencyChanging:Boolean = false,
    )
