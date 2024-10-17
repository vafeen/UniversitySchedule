package ru.vafeen.universityschedule.domain.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Url
import ru.vafeen.universityschedule.data.network.parcelable.github_service.Release
import ru.vafeen.universityschedule.data.network.repository.NetworkRepository
import ru.vafeen.universityschedule.domain.network.usecase.DownloadFileUseCase
import ru.vafeen.universityschedule.domain.network.usecase.GetLatestReleaseUseCase

class NetworkRepositoryImpl(
    private val getLatestReleaseUseCase: GetLatestReleaseUseCase,
    private val downloadFileUseCase: DownloadFileUseCase,
) : NetworkRepository {
    override suspend fun getLatestRelease(): Response<Release>? = getLatestReleaseUseCase()

    override fun downloadFile(@Url fileUrl: String): Call<ResponseBody>? =
        downloadFileUseCase(fileUrl = fileUrl)
}

