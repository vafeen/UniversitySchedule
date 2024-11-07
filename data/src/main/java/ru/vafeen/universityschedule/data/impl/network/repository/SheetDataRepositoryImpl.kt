package ru.vafeen.universityschedule.data.impl.network.repository

import retrofit2.http.Url
import ru.vafeen.universityschedule.data.converters.LessonConverter
import ru.vafeen.universityschedule.data.utils.getLessonsListFromGSheetsTable
import ru.vafeen.universityschedule.domain.models.Lesson
import ru.vafeen.universityschedule.domain.network.repository.SheetDataRepository
import ru.vafeen.universityschedule.domain.network.result_status.sheet_data.ErrorSheetDataResult
import ru.vafeen.universityschedule.domain.network.result_status.sheet_data.SheetDataResult
import ru.vafeen.universityschedule.domain.network.result_status.sheet_data.SuccessSheetDataResult
import ru.vafeen.universityschedule.data.network.service.GoogleSheetsService

internal class SheetDataRepositoryImpl(
    private val googleSheetsService: GoogleSheetsService,
    private val lessonConverter: LessonConverter,
) : SheetDataRepository {
    override suspend fun getLessonsListFromGSheetsTable(@Url link: String): SheetDataResult<List<Lesson>> =
        try {
            SuccessSheetDataResult(
                data = lessonConverter.convertABList(
                    googleSheetsService.getLessonsListFromGSheetsTable(link = link)
                )
            )
        } catch (e: Exception) {
            ErrorSheetDataResult(exception = e)
        }
}