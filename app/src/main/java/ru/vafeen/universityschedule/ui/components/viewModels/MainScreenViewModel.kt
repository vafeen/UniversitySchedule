package ru.vafeen.universityschedule.ui.components.viewModels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import ru.vafeen.universityschedule.R
import ru.vafeen.universityschedule.database.dao.DatabaseRepository
import ru.vafeen.universityschedule.database.entity.Lesson
import ru.vafeen.universityschedule.network.GSheetsService
import ru.vafeen.universityschedule.noui.lesson_additions.Frequency
import ru.vafeen.universityschedule.utils.GSheetsServiceProblem
import ru.vafeen.universityschedule.utils.SharedPreferencesValue
import ru.vafeen.universityschedule.utils.getLessonsListFromGSheetsTable
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.Calendar
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    @ApplicationContext private val context: Context
) :
    ViewModel() {
    val ruDaysOfWeek =
        listOf(
            context.getString(R.string.monday),
            context.getString(R.string.tuesday),
            context.getString(R.string.wednesday),
            context.getString(R.string.thursday),
            context.getString(R.string.friday),
            context.getString(R.string.satudray),
            context.getString(R.string.sunday)
        )
    val daysOfWeek = DayOfWeek.entries.toList()
    val weekOfYear =
        if (Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) % 2 == 0) Frequency.Denominator
        else Frequency.Numerator
    val todayDate: LocalDate = LocalDate.now()
    var nowIsLesson: Boolean = false
    var gSheetsService: GSheetsService? = null
    val link = context.getSharedPreferences(
        SharedPreferencesValue.Name.key, Context.MODE_PRIVATE
    ).getString(SharedPreferencesValue.Link.key, "") ?: ""

    suspend fun updateLocalDatabase(updateUICallback: (List<Lesson>, GSheetsServiceProblem) -> Unit) {
        val lastLessons = databaseRepository.getAllAsFlowLessons().first()
        updateUICallback(
            lastLessons, if (link.isNotEmpty()) GSheetsServiceProblem.Waiting
            else GSheetsServiceProblem.NoLink
        )
        try {
            gSheetsService?.getLessonsListFromGSheetsTable()
                ?.let {
                    databaseRepository.apply {
                        deleteAllLessons(*lastLessons.toTypedArray())
                        insertAllLessons(*it.toTypedArray())
                    }
                    updateUICallback(it, GSheetsServiceProblem.Success)
                }
        } catch (e: Exception) {
            updateUICallback(lastLessons, GSheetsServiceProblem.NetworkError)
        }
    }
}