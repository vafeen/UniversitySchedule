package ru.vafeen.universityschedule.presentation.components.viewModels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.vafeen.universityschedule.domain.GSheetsServiceRequestStatus
import ru.vafeen.universityschedule.domain.models.Settings
import ru.vafeen.universityschedule.domain.usecase.db.GetAsFlowLessonsUseCase
import ru.vafeen.universityschedule.domain.usecase.network.GetSheetDataAndUpdateDBUseCase
import ru.vafeen.universityschedule.domain.utils.getSettingsOrCreateIfNull
import ru.vafeen.universityschedule.domain.utils.save

class x(val sharedPreferences: SharedPreferences) {
    private val _settingsFlow = MutableStateFlow(sharedPreferences.getSettingsOrCreateIfNull())
    val settingsFlow: StateFlow<Settings> = _settingsFlow

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
            CoroutineScope(Dispatchers.IO).launch {
                _settingsFlow.emit(sharedPreferences.getSettingsOrCreateIfNull())
            }
        }
    }

    @Synchronized
    fun save(settings: Settings) {
        sharedPreferences.save(settings = settings)
    }
}

internal class SettingsScreenViewModel(
    private val getAsFlowLessonsUseCase: GetAsFlowLessonsUseCase,
    private val getSheetDataAndUpdateDBUseCase: GetSheetDataAndUpdateDBUseCase,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {
    val x = x(sharedPreferences)
    private var lastLink: String? = null
    val settings = x.settingsFlow


    fun saveSettingsToSharedPreferences(settings: Settings) {
        x.save(settings)
    }

    private val _gSheetsServiceRequestStatusFlow =
        MutableStateFlow<GSheetsServiceRequestStatus>(GSheetsServiceRequestStatus.Waiting)
    val gSheetsServiceRequestStatusFlow = _gSheetsServiceRequestStatusFlow.asStateFlow()

    init {

        viewModelScope.launch(Dispatchers.IO) {
            settings.collect {
                val link = it.link
                when {
                    link.isNullOrEmpty() -> {
                        _gSheetsServiceRequestStatusFlow.emit(GSheetsServiceRequestStatus.NoLink)
                    }

                    link.isNotEmpty() && link != lastLink -> {
                        getSheetDataAndUpdateDBUseCase.use(link = link) { status ->
                            _gSheetsServiceRequestStatusFlow.emit(status)
                        }
                    }
                }
                lastLink = link
            }
        }
    }

    private val _subgroupFlow = MutableStateFlow<List<String>>(listOf())
    val subgroupFlow = _subgroupFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAsFlowLessonsUseCase.use().map { it.mapNotNull { lesson -> lesson.subGroup } }
                .collect { subGroups ->
                    _subgroupFlow.emit(subGroups.distinct())
                }
        }
    }


}
