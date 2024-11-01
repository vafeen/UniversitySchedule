package ru.vafeen.universityschedule.data.network.service

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Service for getting data from Google sheets
 */
interface GSheetsService {
    @GET
    suspend fun getSheetData(@Url link: String): ResponseBody
}