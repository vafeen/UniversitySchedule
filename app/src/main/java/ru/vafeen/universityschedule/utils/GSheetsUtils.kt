package ru.vafeen.universityschedule.utils

import retrofit2.Retrofit
import ru.vafeen.universityschedule.network.GSheetsService

fun createGSheetsService(link: String): GSheetsService? {
    if (link.isEmpty()) return null
    return try {
        Retrofit.Builder().baseUrl(link.replace("edit?usp=sharing", "")).build()
            .create(GSheetsService::class.java)
    } catch (e: Exception) {
        null
    }
}



