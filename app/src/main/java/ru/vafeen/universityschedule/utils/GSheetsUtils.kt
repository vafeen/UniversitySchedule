package ru.vafeen.universityschedule.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vafeen.universityschedule.network.GSheetsService

fun createGSheetsService(link: String): GSheetsService? = try {
    Retrofit.Builder()
        .baseUrl(link.replace("edit?usp=sharing", ""))
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(GSheetsService::class.java)
} catch (e: Exception) {
    null
}



