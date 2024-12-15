package ru.vafeen.universityschedule.data.network.service

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Service for getting data from Google sheets
 */
internal interface GoogleSheetsService {
    @GET
    suspend fun getSheetData(@Url link: String): Response<ResponseBody>
}