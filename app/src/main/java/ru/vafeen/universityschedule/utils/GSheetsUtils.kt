package ru.vafeen.universityschedule.utils

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vafeen.universityschedule.network.GSheetsService
import ru.vafeen.universityschedule.network.parcelable.ResponseWrapper

fun createGSheetsService(link: String): GSheetsService = Retrofit.Builder()
    .baseUrl(link.replace("edit?usp=sharing", ""))
    .addConverterFactory(GsonConverterFactory.create())
    .build().create(GSheetsService::class.java)

// для получения данных достаточно просто открыть доступ и вставить ссылку
suspend fun getData(link: String): String =
    createGSheetsService(link = link).getSheetData().string()

fun getResponse(jsonString: String): ResponseWrapper =
    GsonBuilder().setLenient().create().fromJson(jsonString, ResponseWrapper::class.java)
