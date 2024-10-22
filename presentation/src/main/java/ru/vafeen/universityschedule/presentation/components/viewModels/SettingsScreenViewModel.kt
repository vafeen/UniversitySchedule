package ru.vafeen.universityschedule.presentation.components.viewModels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.vafeen.universityschedule.data.database.DatabaseRepository
import ru.vafeen.universityschedule.data.network.service.GSheetsService
import ru.vafeen.universityschedule.data.utils.GSheetsServiceRequestStatus
import ru.vafeen.universityschedule.data.utils.cleverUpdatingLessons
import ru.vafeen.universityschedule.data.utils.createGSheetsService
import ru.vafeen.universityschedule.data.utils.getLessonsListFromGSheetsTable
import ru.vafeen.universityschedule.domain.Settings
import ru.vafeen.universityschedule.domain.utils.getSettingsOrCreateIfNull
import ru.vafeen.universityschedule.domain.utils.save

internal class SettingsScreenViewModel(
    private val databaseRepository: DatabaseRepository,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {
    private val _settings =
        MutableStateFlow(sharedPreferences.getSettingsOrCreateIfNull())
    val settings = _settings.asStateFlow()
    private val spListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            viewModelScope.launch(Dispatchers.IO) {
                _settings.emit(sharedPreferences.getSettingsOrCreateIfNull())
            }
        }

    fun saveSettingsToSharedPreferences(settings: Settings) {
        sharedPreferences.save(settings = settings)
    }

    private var gSheetsService: GSheetsService? = null
    private var lastLink: String? = null

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(spListener)
        viewModelScope.launch(Dispatchers.IO) {
            settings.collect {
                val link = it.link
                if (link != lastLink) {
                    lastLink = link
                    gSheetsService = link?.let { linkNotNull ->
                        createGSheetsService(
                            link = linkNotNull
                        )
                    }
                    updateLocalDatabaseAndNotifyGSheetsServiceRequestStatusFlow()
                }
            }
        }
    }

    private val _subgroupFlow = MutableStateFlow<List<String>>(listOf())
    val subgroupFlow = _subgroupFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.getAllAsFlowLessons().collect { lessons ->
                _subgroupFlow.emit(lessons.filter {
                    it.subGroup != null
                }.map {
                    it.subGroup.toString()
                }.distinct())
            }
        }
    }

    private val _gSheetsServiceRequestStatusFlow =
        MutableStateFlow(GSheetsServiceRequestStatus.Waiting)
    val gSheetsServiceRequestStatusFlow = _gSheetsServiceRequestStatusFlow.asStateFlow()

    private fun updateLocalDatabaseAndNotifyGSheetsServiceRequestStatusFlow() {
        viewModelScope.launch(Dispatchers.IO) {
            val settings = settings.first()
            _gSheetsServiceRequestStatusFlow.emit(
                if (settings.link?.isNotEmpty() == true) GSheetsServiceRequestStatus.Waiting
                else GSheetsServiceRequestStatus.NoLink
            )
            if (settings.link?.isNotEmpty() == true) try {
                gSheetsService?.getLessonsListFromGSheetsTable()?.let {
                    cleverUpdatingLessons(newLessons = it)
                    _gSheetsServiceRequestStatusFlow.emit(GSheetsServiceRequestStatus.Success)
                }
            } catch (e: Exception) {
                _gSheetsServiceRequestStatusFlow.emit(GSheetsServiceRequestStatus.NetworkError)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(spListener)
    }
}
