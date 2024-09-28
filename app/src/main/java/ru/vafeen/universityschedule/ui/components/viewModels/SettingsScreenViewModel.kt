package ru.vafeen.universityschedule.ui.components.viewModels

import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.vafeen.universityschedule.database.DatabaseRepository
import ru.vafeen.universityschedule.network.service.GSheetsService
import ru.vafeen.universityschedule.utils.GSheetsServiceRequestStatus
import ru.vafeen.universityschedule.noui.shared_preferences.SharedPreferences
import ru.vafeen.universityschedule.utils.cleverUpdatingLessons
import ru.vafeen.universityschedule.utils.createGSheetsService
import ru.vafeen.universityschedule.utils.getLessonsListFromGSheetsTable
import ru.vafeen.universityschedule.utils.getSettingsOrCreateIfNull

class SettingsScreenViewModel(
    val databaseRepository: DatabaseRepository,
    val sharedPreferences: SharedPreferences,
) : ViewModel() {
    val spaceBetweenCards = 30.dp

    //    val noLink = context.getString(R.string.no_link)
    var settings = sharedPreferences.getSettingsOrCreateIfNull()

    var gSheetsService: GSheetsService? = settings.link?.let { createGSheetsService(link = it) }

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
}
