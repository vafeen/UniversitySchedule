package ru.vafeen.universityschedule.presentation.features.settings_screen

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.vafeen.universityschedule.domain.GSheetsServiceRequestStatus
import ru.vafeen.universityschedule.domain.network.service.SettingsManager
import ru.vafeen.universityschedule.domain.usecase.CatMeowUseCase
import ru.vafeen.universityschedule.domain.usecase.db.GetAsFlowLessonsUseCase
import ru.vafeen.universityschedule.domain.usecase.network.GetSheetDataAndUpdateDBUseCase
import ru.vafeen.universityschedule.presentation.components.viewModels.BaseStateViewModel

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
) : BaseStateViewModel<SettingsScreenState, SettingsScreenEvent>() {
    override val _state = MutableStateFlow(SettingsScreenState())
    override val state: StateFlow<SettingsScreenState> = _state

    init {
        viewModelScope.launch(Dispatchers.IO) {
            settingsManager.settingsFlow.collect { s ->
                updateState {
                    it.copy(settings = s)
                }
                val link = s.link
                when {
                    link.isNullOrEmpty() -> {
                        // Если ссылка пустая, устанавливаем статус NoLink
                        sendEvent(
                            SettingsScreenEvent.GSheetsRequestStatusUpdate(
                                GSheetsServiceRequestStatus.NoLink
                            )
                        )
                    }

                    link.isNotEmpty() && link != lastLink -> {
                        // Если ссылка изменена, выполняем запрос на получение данных из Google Sheets
                        getSheetDataAndUpdateDBUseCase.invoke(link = link) { status ->
                            sendEvent(SettingsScreenEvent.GSheetsRequestStatusUpdate(status))
                        }
                    }
                }
                lastLink = link
            }
        }
    }

    override fun sendEvent(event: SettingsScreenEvent) {
        when (event) {
            /**
             * Функция для выполнения действия "мяу".
             */
            SettingsScreenEvent.MeowEvent -> {
                catMeowUseCase.invoke()
            }

            is SettingsScreenEvent.SaveSettingsEvent -> {
                settingsManager.save(event.saving)
            }

            is SettingsScreenEvent.GSheetsRequestStatusUpdate -> {
                updateState {
                    it.copy(gSheetsServiceRequestStatus = event.newStatus)
                }
            }
        }
    }


    // Переменная для хранения последнего использованного ссылки
    private var lastLink: String? = null


    init {
        // Инициализируем поток для получения подгрупп из базы данных
        viewModelScope.launch(Dispatchers.IO) {
            getAsFlowLessonsUseCase.invoke().map { it.mapNotNull { lesson -> lesson.subGroup } }
                .collect { subGroups ->
                    // Эмитируем только уникальные подгруппы
                    updateState {
                        it.copy(subGroups = subGroups.distinct())
                    }
                }
        }
    }


}
