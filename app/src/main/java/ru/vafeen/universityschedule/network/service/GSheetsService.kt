package ru.vafeen.universityschedule.network.service

import okhttp3.ResponseBody
import retrofit2.http.GET

/**
 * Service for getting data from Google sheets
 */
interface GSheetsService {
    @GET("gviz/tq?tqx=out:json")
    suspend fun getSheetData(): ResponseBody

}