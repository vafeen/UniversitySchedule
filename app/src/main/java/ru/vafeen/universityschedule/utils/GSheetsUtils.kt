package ru.vafeen.universityschedule.utils

import retrofit2.Retrofit
import ru.vafeen.universityschedule.network.GSheetsService

fun createGSheetsService(link: String): GSheetsService? = try {
    Retrofit.Builder()
        .baseUrl(link.replace("edit?usp=sharing", ""))
        .build().create(GSheetsService::class.java)
} catch (e: Exception) {
    null
}



