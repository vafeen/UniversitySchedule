package ru.vafeen.universityschedule.domain.usecase.network

import okhttp3.ResponseBody
import org.koin.java.KoinJavaComponent.inject
import retrofit2.Call
import retrofit2.http.Url
import ru.vafeen.universityschedule.domain.network.repository.NetworkRepository

class DownloadFileUseCase {
    private val networkRepository: NetworkRepository by inject(clazz = NetworkRepository::class.java)
    fun use(@Url fileUrl: String): Call<ResponseBody>? =
        networkRepository.downloadFile(fileUrl = fileUrl)
}