package ru.vafeen.universityschedule.network

import okhttp3.ResponseBody
import retrofit2.http.GET

interface GSheetsService {
    @GET("gviz/tq?tqx=out:json")
    suspend fun getSheetData(): ResponseBody

}