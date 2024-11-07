package ru.vafeen.universityschedule.domain.network.repository

import retrofit2.http.Url
import ru.vafeen.universityschedule.domain.models.Lesson
import ru.vafeen.universityschedule.domain.network.result_status.sheet_data.SheetDataResult

interface SheetDataRepository {
    suspend fun getLessonsListFromGSheetsTable(@Url link: String): SheetDataResult<List<Lesson>>
}