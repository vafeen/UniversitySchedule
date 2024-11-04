package ru.vafeen.universityschedule.data.impl.network.repository

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Url
import ru.vafeen.universityschedule.domain.network.repository.DownloadFileRepository
import ru.vafeen.universityschedule.domain.network.service.DownloadService

internal class DownloadFileRepositoryImpl(private val downloadService: DownloadService) :
    DownloadFileRepository {
    override fun downloadFile(@Url fileUrl: String): Call<ResponseBody>? = try {
        downloadService.downloadFile(fileUrl = fileUrl)
    } catch (e: Exception) {
        null
    }
}