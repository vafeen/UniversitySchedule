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

/**
 * ViewModel для экрана настроек.
 * Управляет состоянием, связанным с настройками, обновлениями из Google Sheets и запросами на сервер.
 *
 * @param getAsFlowLessonsUseCase UseCase для получения данных о занятиях.
 * @param getSheetDataAndUpdateDBUseCase UseCase для получения данных из Google Sheets и обновления базы данных.
 * @param catMeowUseCase UseCase для выполнения действия "мяу".
 * @param settingsManager Менеджер для работы с настройками приложения.
 */
internal class SettingsScreenViewModel(
    private val getAsFlowLessonsUseCase: GetAsFlowLessonsUseCase,
    private val getSheetDataAndUpdateDBUseCase: GetSheetDataAndUpdateDBUseCase,
    private val catMeowUseCase: CatMeowUseCase,
    private val settingsManager: SettingsManager,
) : ViewModel() {

    // Переменная для хранения последнего использованного ссылки
    private var lastLink: String? = null

    // Поток состояний для хранения настроек приложения
    val settings = settingsManager.settingsFlow

    /**
     * Функция для выполнения действия "мяу".
     */
    fun meow() {
        catMeowUseCase.invoke()
    }

    /**
     * Сохраняет изменения настроек в SharedPreferences.
     *
     * @param saving Функция, изменяющая текущие настройки.
     */
    fun saveSettingsToSharedPreferences(saving: (Settings) -> Settings) {
        settingsManager.save(saving)
    }

    // Поток состояний для статуса запроса к Google Sheets
    private val _gSheetsServiceRequestStatusFlow =
        MutableStateFlow<GSheetsServiceRequestStatus>(GSheetsServiceRequestStatus.Waiting)
    val gSheetsServiceRequestStatusFlow = _gSheetsServiceRequestStatusFlow.asStateFlow()

    init {
        // Инициализируем поток для отслеживания изменений в настройках
        viewModelScope.launch(Dispatchers.IO) {
            settings.collect {
                val link = it.link
                when {
                    link.isNullOrEmpty() -> {
                        // Если ссылка пустая, устанавливаем статус NoLink
                        _gSheetsServiceRequestStatusFlow.emit(GSheetsServiceRequestStatus.NoLink)
                    }

                    link.isNotEmpty() && link != lastLink -> {
                        // Если ссылка изменена, выполняем запрос на получение данных из Google Sheets
                        getSheetDataAndUpdateDBUseCase.invoke(link = link) { status ->
                            _gSheetsServiceRequestStatusFlow.emit(status)
                        }
                    }
                }
                lastLink = link
            }
        }
    }

    // Поток состояний для хранения списка подгрупп
    private val _subgroupFlow = MutableStateFlow<List<String>>(listOf())
    val subgroupFlow = _subgroupFlow.asStateFlow()

    init {
        // Инициализируем поток для получения подгрупп из базы данных
        viewModelScope.launch(Dispatchers.IO) {
            getAsFlowLessonsUseCase.invoke().map { it.mapNotNull { lesson -> lesson.subGroup } }
                .collect { subGroups ->
                    // Эмитируем только уникальные подгруппы
                    _subgroupFlow.emit(subGroups.distinct())
                }
        }
    }
}
