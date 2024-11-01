package ru.vafeen.universityschedule.presentation.components.viewModels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.vafeen.universityschedule.domain.GSheetsServiceRequestStatus
import ru.vafeen.universityschedule.domain.Settings
import ru.vafeen.universityschedule.domain.usecase.db.GetAsFlowLessonsUseCase
import ru.vafeen.universityschedule.domain.usecase.network.GetSheetDataAndUpdateDBUseCase
import ru.vafeen.universityschedule.domain.utils.getSettingsOrCreateIfNull
import ru.vafeen.universityschedule.domain.utils.save

internal class SettingsScreenViewModel(
    private val getAsFlowLessonsUseCase: GetAsFlowLessonsUseCase,
    private val getSheetDataAndUpdateDBUseCase: GetSheetDataAndUpdateDBUseCase,
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

    private var lastLink: String? = null

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(spListener)
        viewModelScope.launch(Dispatchers.IO) {
            settings.collect {
                val link = it.link
                if (link != lastLink) {
                    lastLink = link
                    updateLocalDatabaseAndNotifyGSheetsServiceRequestStatusFlow()
                }
            }
        }
    }

    private val _subgroupFlow = MutableStateFlow<List<String>>(listOf())
    val subgroupFlow = _subgroupFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAsFlowLessonsUseCase().map {
                it.mapNotNull { lessonEntity ->
                    lessonEntity.subGroup
                }
            }.collect { subGroups ->
                _subgroupFlow.emit(subGroups.distinct())
            }
        }
    }

    private val _gSheetsServiceRequestStatusFlow =
        MutableStateFlow(GSheetsServiceRequestStatus.Waiting)
    val gSheetsServiceRequestStatusFlow = _gSheetsServiceRequestStatusFlow.asStateFlow()

    private fun updateLocalDatabaseAndNotifyGSheetsServiceRequestStatusFlow() {
        viewModelScope.launch(Dispatchers.IO) {
            val link = settings.first().link
            if (link.isNullOrEmpty())
                _gSheetsServiceRequestStatusFlow.emit(GSheetsServiceRequestStatus.NoLink)
            else {
                getSheetDataAndUpdateDBUseCase.invoke(link = link) {
                    _gSheetsServiceRequestStatusFlow.emit(it)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(spListener)
    }
}
