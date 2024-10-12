package ru.vafeen.universityschedule.ui.components.viewModels

import android.content.SharedPreferences
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.vafeen.universityschedule.database.DatabaseRepository
import ru.vafeen.universityschedule.network.service.GSheetsService
import ru.vafeen.universityschedule.ui.components.Settings
import ru.vafeen.universityschedule.utils.GSheetsServiceRequestStatus
import ru.vafeen.universityschedule.utils.cleverUpdatingLessons
import ru.vafeen.universityschedule.utils.createGSheetsService
import ru.vafeen.universityschedule.utils.getLessonsListFromGSheetsTable
import ru.vafeen.universityschedule.utils.getSettingsOrCreateIfNull

class SettingsScreenViewModel(
    val databaseRepository: DatabaseRepository,
    val sharedPreferences: SharedPreferences,
) : ViewModel() {
    val spaceBetweenCards = 30.dp

    private val _settings =
        MutableStateFlow<Settings>(sharedPreferences.getSettingsOrCreateIfNull())
    val settings = _settings.asStateFlow()
    val spListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            _settings.value = sharedPreferences.getSettingsOrCreateIfNull()
        }
    var gSheetsService: GSheetsService? = null



    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(spListener)
        viewModelScope.launch(Dispatchers.IO) {
            settings.collect {
                gSheetsService = it.link?.let { createGSheetsService(link = it) }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(spListener)
    }


    fun updateLocalDatabase(updateUICallback: (GSheetsServiceRequestStatus) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                updateUICallback(
                    if (settings.first().link?.isNotEmpty() == true) GSheetsServiceRequestStatus.Waiting
                    else GSheetsServiceRequestStatus.NoLink
                )
            }
            if (settings.first().link?.isNotEmpty() == true)
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
