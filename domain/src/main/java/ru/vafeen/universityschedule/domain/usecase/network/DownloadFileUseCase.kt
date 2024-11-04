package ru.vafeen.universityschedule.domain.usecase.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Url
import ru.vafeen.universityschedule.domain.network.repository.DownloadFileRepository
import ru.vafeen.universityschedule.domain.usecase.base.UseCase

class DownloadFileUseCase(private val downloadFileRepository: DownloadFileRepository) : UseCase {
    fun use(@Url fileUrl: String): Call<ResponseBody>? =
        downloadFileRepository.downloadFile(fileUrl = fileUrl)
}