package ru.vafeen.universityschedule.domain.usecase.network

import retrofit2.http.Url
import ru.vafeen.universityschedule.domain.models.CompositeFileStream
import ru.vafeen.universityschedule.domain.network.result.ResponseResult
import ru.vafeen.universityschedule.domain.network.service.DownloadFileRepository
import ru.vafeen.universityschedule.domain.usecase.base.UseCase

class DownloadFileUseCase(private val downloadFileRepository: DownloadFileRepository) : UseCase {
    suspend fun invoke(@Url fileUrl: String): ResponseResult<CompositeFileStream> =
        downloadFileRepository.downloadFile(fileUrl = fileUrl)
}