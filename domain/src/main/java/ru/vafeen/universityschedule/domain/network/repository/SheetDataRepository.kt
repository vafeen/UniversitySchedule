package ru.vafeen.universityschedule.domain.network.repository

import retrofit2.http.Url
import ru.vafeen.universityschedule.domain.models.Lesson
import ru.vafeen.universityschedule.domain.network.result.ResponseResult

/**
 * Интерфейс репозитория для работы с данными таблиц Google Sheets.
 */
interface SheetDataRepository {

    /**
     * Получает список уроков из таблицы Google Sheets по указанному URL.
     *
     * @param link URL таблицы Google Sheets, откуда нужно получить данные.
     * @return [ResponseResult] с результатом запроса, содержащим список объектов [Lesson].
     */
    suspend fun getLessonsListFromGSheetsTable(@Url link: String): ResponseResult<List<Lesson>>
}