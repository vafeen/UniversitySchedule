package ru.vafeen.universityschedule.ui.components.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.first
import ru.vafeen.universityschedule.database.dao.DatabaseRepository
import ru.vafeen.universityschedule.database.entity.Lesson
import ru.vafeen.universityschedule.network.GSheetsService
import ru.vafeen.universityschedule.noui.lesson_additions.Frequency
import ru.vafeen.universityschedule.utils.getLessonsListFromGSheetsTable
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.Calendar
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(private val databaseRepository: DatabaseRepository) :
    ViewModel() {
    val ruDaysOfWeek = listOf("ПН", "ВТ", "СР", "ЧТ", "ПТ", "СБ", "ВС")
    val daysOfWeek = DayOfWeek.entries.toList()
    val weekOfYear =
        if (Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) % 2 == 0) Frequency.Denominator
        else Frequency.Numerator
    val todayDate: LocalDate = LocalDate.now()
    var nowIsLesson: Boolean = false
    var gSheetsService: GSheetsService? = null

    suspend fun updateLocalDatabase(updateUICallback: (List<Lesson>) -> Unit) {
        val lastLessons = databaseRepository.getAllAsFlowLessons().first()
        updateUICallback(lastLessons)
        gSheetsService?.getLessonsListFromGSheetsTable()
            ?.let {
                databaseRepository.apply {
                    deleteAllLessons(*lastLessons.toTypedArray())
                    insertAllLessons(*it.toTypedArray())
                }
                updateUICallback(it)
            }
    }
}