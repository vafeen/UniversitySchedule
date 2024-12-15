package ru.vafeen.universityschedule.domain.usecase.network

import ru.vafeen.universityschedule.domain.GSheetsServiceRequestStatus
import ru.vafeen.universityschedule.domain.network.result.ResponseResult
import ru.vafeen.universityschedule.domain.usecase.base.UseCase
import ru.vafeen.universityschedule.domain.usecase.db.CleverUpdatingLessonsUseCase

class GetSheetDataAndUpdateDBUseCase(
    private val getSheetDataUseCase: GetSheetDataUseCase,
    private val cleverUpdatingLessonsUseCase: CleverUpdatingLessonsUseCase,
) : UseCase {
    suspend fun use(
        link: String,
        updateRequestStatus: (suspend (GSheetsServiceRequestStatus) -> Unit)? = null
    ) {
        updateRequestStatus?.invoke(GSheetsServiceRequestStatus.Waiting)
        getSheetDataUseCase.use(link).also {
            when (it) {
                is ResponseResult.Error -> {
                    updateRequestStatus?.invoke(GSheetsServiceRequestStatus.NetworkError)
                }

                is ResponseResult.Success -> {
                    cleverUpdatingLessonsUseCase.use(it.data)
                    updateRequestStatus?.invoke(GSheetsServiceRequestStatus.Success)
                }
            }
        }
    }
}