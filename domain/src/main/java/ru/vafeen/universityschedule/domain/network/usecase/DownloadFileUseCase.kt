package ru.vafeen.universityschedule.domain.network.usecase

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Url
import ru.vafeen.universityschedule.data.network.service.DownloadService

internal class DownloadFileUseCase(private val downloadService: DownloadService) {
    operator fun invoke(@Url fileUrl: String): Call<ResponseBody>? = try {
        downloadService.downloadFile(fileUrl = fileUrl)
    } catch (_: Exception) {
        null
    }
}