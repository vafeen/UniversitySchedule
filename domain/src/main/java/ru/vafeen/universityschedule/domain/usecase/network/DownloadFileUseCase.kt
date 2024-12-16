package ru.vafeen.universityschedule.domain.usecase.network

import retrofit2.http.Url
import ru.vafeen.universityschedule.domain.models.CompositeFileStream
import ru.vafeen.universityschedule.domain.network.result.ResponseResult
import ru.vafeen.universityschedule.domain.network.service.DownloadFileRepository
import ru.vafeen.universityschedule.domain.usecase.base.UseCase

/**
 * UseCase для загрузки файлов из сети.
 *
 * Этот класс отвечает за выполнение операции загрузки файла с указанного URL.
 *
 * @property downloadFileRepository Репозиторий, используемый для взаимодействия с сетевыми запросами на загрузку файлов.
 */
class DownloadFileUseCase(private val downloadFileRepository: DownloadFileRepository) : UseCase {

    /**
     * Загружает файл по указанному URL.
     *
     * @param fileUrl URL файла, который нужно загрузить. Аннотация [Url] указывает, что параметр должен быть корректным URL.
     * @return [ResponseResult] с потоком загруженного файла [CompositeFileStream].
     */
    suspend fun invoke(@Url fileUrl: String): ResponseResult<CompositeFileStream> =
        downloadFileRepository.downloadFile(fileUrl = fileUrl)
}
