package ru.vafeen.universityschedule.domain.network.repository

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Url

interface DownloadFileRepository {
    fun downloadFile(@Url fileUrl: String): Call<ResponseBody>?
}