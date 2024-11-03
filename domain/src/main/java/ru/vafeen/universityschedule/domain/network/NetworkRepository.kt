package ru.vafeen.universityschedule.domain.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Url
import ru.vafeen.universityschedule.domain.models.Lesson
import ru.vafeen.universityschedule.domain.models.Release


interface NetworkRepository {
    suspend fun getLatestRelease(): Response<Release>?
    fun downloadFile(@Url fileUrl: String): Call<ResponseBody>?
    suspend fun getLessonsListFromGSheetsTable(@Url link: String): List<Lesson>?
}