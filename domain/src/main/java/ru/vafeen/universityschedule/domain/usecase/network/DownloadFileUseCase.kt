package ru.vafeen.universityschedule.domain.usecase.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Url
import ru.vafeen.universityschedule.domain.network.NetworkRepository
import ru.vafeen.universityschedule.domain.usecase.base.UseCase

class DownloadFileUseCase(private val networkRepository: NetworkRepository) : UseCase {
    fun use(@Url fileUrl: String): Call<ResponseBody>? =
        networkRepository.downloadFile(fileUrl = fileUrl)
}