package ru.vafeen.universityschedule.domain.usecase.network

import ru.vafeen.universityschedule.domain.GSheetsServiceRequestStatus
import ru.vafeen.universityschedule.domain.usecase.db.CleverUpdatingLessonsUseCase

class GetSheetDataAndUpdateDBUseCase(
    private val getSheetDataUseCase: GetSheetDataUseCase,
    private val cleverUpdatingLessonsUseCase: CleverUpdatingLessonsUseCase,
) {
    suspend fun use(
        link: String,
        updateRequestStatus: suspend (GSheetsServiceRequestStatus) -> Unit
    ) {
        updateRequestStatus(GSheetsServiceRequestStatus.Waiting)
        try {
            use(link = link)
            updateRequestStatus(GSheetsServiceRequestStatus.Success)
        } catch (e: Exception) {
            updateRequestStatus(GSheetsServiceRequestStatus.NetworkError)
        }
    }

    suspend fun use(link: String) {
        getSheetDataUseCase.use(link)?.let { cleverUpdatingLessonsUseCase.use(it) }
    }

}