package ru.vafeen.universityschedule.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vafeen.universityschedule.network.GSheetsService

fun createGSheetsService(id: String): GSheetsService = Retrofit.Builder()
    .baseUrl("https://docs.google.com/spreadsheets/d/${id}/")
    .addConverterFactory(GsonConverterFactory.create())
    .build().create(GSheetsService::class.java)
