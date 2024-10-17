package ru.vafeen.universityschedule.data.network.repository

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Url
import ru.vafeen.universityschedule.data.network.parcelable.github_service.Release


interface NetworkRepository {
    suspend fun getLatestRelease(): Response<Release>?
    fun downloadFile(@Url fileUrl: String): Call<ResponseBody>?
}