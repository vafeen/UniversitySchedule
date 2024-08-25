package ru.vafeen.universityschedule.network.service

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface DownloadService {
    @GET
    @Streaming
    fun downloadFile(@Url fileUrl: String): Call<ResponseBody>
}
