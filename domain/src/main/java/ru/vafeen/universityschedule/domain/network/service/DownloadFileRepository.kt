package ru.vafeen.universityschedule.domain.network.service

import ru.vafeen.universityschedule.domain.models.CompositeFileStream
import ru.vafeen.universityschedule.domain.network.result.ResponseResult

/**
 * Интерфейс репозитория для загрузки файлов.
 */
interface DownloadFileRepository {

    /**
     * Загружает файл по указанному URL.
     *
     * @param fileUrl URL файла, который нужно загрузить.
     * @return [ResponseResult] с результатом загрузки, содержащим объект [CompositeFileStream].
     */
    suspend fun downloadFile(fileUrl: String): ResponseResult<CompositeFileStream>
}
