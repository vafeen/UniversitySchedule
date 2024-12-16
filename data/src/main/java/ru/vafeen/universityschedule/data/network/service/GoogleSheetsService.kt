package ru.vafeen.universityschedule.data.network.service

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Сервис для получения данных из Google Sheets.
 */
internal interface GoogleSheetsService {

    /**
     * Получает данные из таблицы Google по указанному URL.
     *
     * @param link URL таблицы Google, откуда нужно получить данные.
     * @return [Response] с телом ответа [ResponseBody], содержащим данные таблицы.
     */
    @GET
    suspend fun getSheetData(@Url link: String): Response<ResponseBody>
}
