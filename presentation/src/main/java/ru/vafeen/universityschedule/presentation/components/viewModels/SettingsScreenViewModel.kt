package ru.vafeen.universityschedule.presentation.components.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.vafeen.universityschedule.domain.GSheetsServiceRequestStatus
import ru.vafeen.universityschedule.domain.models.Settings
import ru.vafeen.universityschedule.domain.network.service.SettingsManager
import ru.vafeen.universityschedule.domain.usecase.CatMeowUseCase
import ru.vafeen.universityschedule.domain.usecase.db.GetAsFlowLessonsUseCase
import ru.vafeen.universityschedule.domain.usecase.network.GetSheetDataAndUpdateDBUseCase


internal class SettingsScreenViewModel(
    private val getAsFlowLessonsUseCase: GetAsFlowLessonsUseCase,
    private val getSheetDataAndUpdateDBUseCase: GetSheetDataAndUpdateDBUseCase,
    private val catMeowUseCase: CatMeowUseCase,
    private val settingsManager: SettingsManager,
) : ViewModel() {

    private var lastLink: String? = null
    val settings = settingsManager.settingsFlow

    fun meow() {
        catMeowUseCase.invoke()
    }
    fun saveSettingsToSharedPreferences(saving: (Settings) -> Settings) {
        settingsManager.save(saving)
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
                        getSheetDataAndUpdateDBUseCase.invoke(link = link) { status ->
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
            getAsFlowLessonsUseCase.invoke().map { it.mapNotNull { lesson -> lesson.subGroup } }
                .collect { subGroups ->
                    _subgroupFlow.emit(subGroups.distinct())
                }
        }
    }


}
