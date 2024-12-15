package ru.vafeen.universityschedule.domain.network.service


import ru.vafeen.universityschedule.domain.models.CompositeFileStream
import ru.vafeen.universityschedule.domain.network.result.ResponseResult


interface DownloadFileRepository {
    suspend fun downloadFile(fileUrl: String): ResponseResult<CompositeFileStream>
}