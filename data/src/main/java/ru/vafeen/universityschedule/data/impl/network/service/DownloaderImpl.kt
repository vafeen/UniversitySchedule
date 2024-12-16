package ru.vafeen.universityschedule.data.impl.network.service

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.vafeen.universityschedule.domain.network.result.ResponseResult
import ru.vafeen.universityschedule.domain.network.service.DownloadFileRepository
import ru.vafeen.universityschedule.domain.network.service.Downloader
import ru.vafeen.universityschedule.domain.network.service.Installer
import ru.vafeen.universityschedule.domain.utils.pathToDownloadRelease
import java.io.File
import java.io.FileOutputStream

/**
 * Реализация загрузчика для скачивания APK файлов.
 *
 * @property context Контекст приложения.
 * @property downloadFileRepository Репозиторий для загрузки файлов.
 * @property installer Установщик для установки загруженного APK.
 */
internal class DownloaderImpl(
    private val context: Context,
    private val downloadFileRepository: DownloadFileRepository,
    private val installer: Installer,
) : Downloader {

    /**
     * Поток для отслеживания процента загрузки.
     */
    private val _percentageFlow = MutableSharedFlow<Float>()
    override val percentageFlow = _percentageFlow.asSharedFlow()

    /**
     * Поток для отслеживания статуса процесса обновления.
     */
    private val _isUpdateInProcessFlow = MutableSharedFlow<Boolean>()
    override val isUpdateInProcessFlow = _isUpdateInProcessFlow.asSharedFlow()

    /**
     * Загрузка APK файла по указанному URL.
     *
     * @param url URL для загрузки APK файла.
     */
    override fun downloadApk(url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            _isUpdateInProcessFlow.emit(true) // Устанавливаем статус обновления в процессе
            val apkFilePath = context.pathToDownloadRelease() // Получаем путь для сохранения APK

            when (val result = downloadFileRepository.downloadFile(url)) {
                is ResponseResult.Error -> {
                    _isUpdateInProcessFlow.emit(false) // Обновление завершено с ошибкой
                    _percentageFlow.emit(0f) // Процент загрузки 0%
                }

                is ResponseResult.Success -> {
                    val file = File(apkFilePath) // Создаем объект файла для сохранения APK
                    val inputStream = result.data.stream // Получаем InputStream из результата загрузки
                    val outputStream = FileOutputStream(file) // Создаем поток для записи в файл
                    val buffer = ByteArray(8 * 1024) // Буфер для чтения данных (8 КБ)
                    var bytesRead: Int // Переменная для хранения количества прочитанных байтов
                    var totalBytesRead: Long = 0 // Общая длина загруженных байтов
                    val contentLength = result.data.contentLength // Общая длина загружаемого контента

                    // Чтение данных из InputStream и запись в файл
                    while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                        outputStream.write(buffer, 0, bytesRead)
                        totalBytesRead += bytesRead
                        _percentageFlow.emit(totalBytesRead.toFloat() / contentLength) // Обновляем процент загрузки
                    }

                    outputStream.close() // Закрываем поток записи
                    inputStream.close() // Закрываем поток чтения
                    _isUpdateInProcessFlow.emit(false) // Обновление завершено успешно
                    installer.installApk() // Устанавливаем загруженный APK
                }
            }
        }
    }
}