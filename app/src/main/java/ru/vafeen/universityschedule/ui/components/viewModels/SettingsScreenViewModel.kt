package ru.vafeen.universityschedule.ui.components.viewModels

import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.vafeen.universityschedule.database.DatabaseRepository
import ru.vafeen.universityschedule.database.entity.Lesson
import ru.vafeen.universityschedule.network.service.GSheetsService
import ru.vafeen.universityschedule.utils.GSheetsServiceRequestStatus
import ru.vafeen.universityschedule.noui.shared_preferences.SharedPreferences
import ru.vafeen.universityschedule.utils.createGSheetsService
import ru.vafeen.universityschedule.utils.getLessonsListFromGSheetsTable
import ru.vafeen.universityschedule.utils.getSettingsOrCreateIfNull

class SettingsScreenViewModel(
    private val databaseRepository: DatabaseRepository,
    val sharedPreferences: SharedPreferences,
) : ViewModel() {
    val spaceBetweenCards = 30.dp

    //    val noLink = context.getString(R.string.no_link)
    var settings = sharedPreferences.getSettingsOrCreateIfNull()

    var gSheetsService: GSheetsService? = settings.link?.let { createGSheetsService(link = it) }

    fun updateLocalDatabase(updateUICallback: (List<Lesson>, GSheetsServiceRequestStatus) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val lastLessons = databaseRepository.getAllAsFlowLessons().first()
            withContext(Dispatchers.Main) {
                updateUICallback(
                    lastLessons,
                    if (settings.link?.isNotEmpty() == true) GSheetsServiceRequestStatus.Waiting
                    else GSheetsServiceRequestStatus.NoLink
                )
            }
            if (settings.link?.isNotEmpty() == true) {
                try {
                    var lessons = listOf<Lesson>()

                    gSheetsService?.getLessonsListFromGSheetsTable()?.let {
                        lessons = it
                        databaseRepository.apply {
                            deleteAllLessons(*lastLessons.toTypedArray())
                            insertAllLessons(*it.toTypedArray())
                        }
                    }
                    withContext(Dispatchers.Main) {
                        updateUICallback(lessons, GSheetsServiceRequestStatus.Success)
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        updateUICallback(lastLessons, GSheetsServiceRequestStatus.NetworkError)
                    }
                }
            }
        }
    }
}
