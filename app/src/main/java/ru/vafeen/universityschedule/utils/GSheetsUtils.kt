package ru.vafeen.universityschedule.utils

import retrofit2.Retrofit
import ru.vafeen.universityschedule.R
import ru.vafeen.universityschedule.network.service.GSheetsService

fun createGSheetsService(link: String): GSheetsService? {
    if (link.isEmpty()) return null
    return try {
        Retrofit.Builder().baseUrl(link.substringBefore("edit?")).build()
            .create(GSheetsService::class.java)
    } catch (e: Exception) {
        null
    }
}

fun getIconByRequestStatus(networkState: GSheetsServiceRequestStatus): Int =
    when (networkState) {
        GSheetsServiceRequestStatus.Waiting -> {
            R.drawable.sync
        }

        GSheetsServiceRequestStatus.Success -> {
            R.drawable.updated

        }

        GSheetsServiceRequestStatus.NetworkError -> {
            R.drawable.no_wifi
        }

        GSheetsServiceRequestStatus.NoLink -> {
            R.drawable.no_link
        }
    }



