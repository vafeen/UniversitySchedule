package ru.vafeen.universityschedule.data.network.service

import okhttp3.ResponseBody
import retrofit2.http.GET
import ru.vafeen.universityschedule.data.network.end_points.GSServiceLink

/**
 * Service for getting data from Google sheets
 */
interface GSheetsService {
    @GET(GSServiceLink.EndPoint.JSON)
    suspend fun getSheetData(): ResponseBody

}