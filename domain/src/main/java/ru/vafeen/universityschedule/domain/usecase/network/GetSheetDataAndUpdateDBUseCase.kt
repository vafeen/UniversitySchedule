package ru.vafeen.universityschedule.domain.usecase.network

import ru.vafeen.universityschedule.domain.GSheetsServiceRequestStatus
import ru.vafeen.universityschedule.domain.network.result.ResponseResult
import ru.vafeen.universityschedule.domain.usecase.base.UseCase
import ru.vafeen.universityschedule.domain.usecase.db.CleverUpdatingLessonsUseCase

/**
 * UseCase для получения данных из Google Sheets и обновления базы данных.
 *
 * Этот класс отвечает за выполнение операции получения данных из указанной таблицы Google Sheets
 * и обновления базы данных на основе полученных данных.
 *
 * @property getSheetDataUseCase UseCase для получения данных из Google Sheets.
 * @property cleverUpdatingLessonsUseCase UseCase для интеллектуального обновления пар.
 */
class GetSheetDataAndUpdateDBUseCase(
    private val getSheetDataUseCase: GetSheetDataUseCase,
    private val cleverUpdatingLessonsUseCase: CleverUpdatingLessonsUseCase,
) : UseCase {

    /**
     * Получает данные из Google Sheets и обновляет базу данных.
     *
     * @param link Ссылка на таблицу Google Sheets, откуда нужно получить данные.
     * @param updateRequestStatus Функция обратного вызова для обновления статуса запроса (по умолчанию null).
     */
    suspend fun invoke(
        link: String,
        updateRequestStatus: (suspend (GSheetsServiceRequestStatus) -> Unit)? = null
    ) {
        updateRequestStatus?.invoke(GSheetsServiceRequestStatus.Waiting) // Устанавливаем статус ожидания
        getSheetDataUseCase.invoke(link).also {
            when (it) {
                is ResponseResult.Error -> {
                    updateRequestStatus?.invoke(GSheetsServiceRequestStatus.NetworkError) // Обработка ошибки сети
                }

                is ResponseResult.Success -> {
                    cleverUpdatingLessonsUseCase.invoke(it.data) // Обновление базы данных с полученными данными
                    updateRequestStatus?.invoke(GSheetsServiceRequestStatus.Success) // Устанавливаем статус успеха
                }
            }
        }
    }
}
