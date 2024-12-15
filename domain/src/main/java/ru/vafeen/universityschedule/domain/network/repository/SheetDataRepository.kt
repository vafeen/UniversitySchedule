package ru.vafeen.universityschedule.domain.network.repository

import retrofit2.http.Url
import ru.vafeen.universityschedule.domain.models.Lesson
import ru.vafeen.universityschedule.domain.network.result.ResponseResult

interface SheetDataRepository {
    suspend fun getLessonsListFromGSheetsTable(@Url link: String): ResponseResult<List<Lesson>>
}