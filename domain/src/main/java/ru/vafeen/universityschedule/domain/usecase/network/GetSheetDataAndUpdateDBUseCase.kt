package ru.vafeen.universityschedule.domain.usecase.network

import ru.vafeen.universityschedule.domain.GSheetsServiceRequestStatus
import ru.vafeen.universityschedule.domain.usecase.db.CleverUpdatingLessonsUseCase

class GetSheetDataAndUpdateDBUseCase(
    private val getSheetDataUseCase: GetSheetDataUseCase,
    private val cleverUpdatingLessonsUseCase: CleverUpdatingLessonsUseCase,
) {
    suspend operator fun invoke(
        link: String,
        updateRequestStatus: suspend (GSheetsServiceRequestStatus) -> Unit
    ) {
        updateRequestStatus(GSheetsServiceRequestStatus.Waiting)
        try {
            invoke(link = link)
            updateRequestStatus(GSheetsServiceRequestStatus.Success)
        } catch (e: Exception) {
            updateRequestStatus(GSheetsServiceRequestStatus.NetworkError)
        }
    }

    suspend operator fun invoke(link: String) {
        getSheetDataUseCase.invoke(link)?.let { cleverUpdatingLessonsUseCase.invoke(it) }
    }

}