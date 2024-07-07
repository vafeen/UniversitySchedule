package ru.vafeen.universityschedule.ui.components.viewModels

import androidx.lifecycle.ViewModel
import ru.vafeen.universityschedule.noui.lesson_additions.Frequency
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.Calendar
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
) : ViewModel() {
    val ruDaysOfWeek = listOf("ПН", "ВТ", "СР", "ЧТ", "ПТ", "СБ", "ВС")
    val daysOfWeek = DayOfWeek.entries.toList()
    val weekOfYear =
        if (Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) % 2 == 0) Frequency.Denominator
        else Frequency.Numerator
    val todayDate: LocalDate = LocalDate.now()
    var nowIsLesson: Boolean = false

}