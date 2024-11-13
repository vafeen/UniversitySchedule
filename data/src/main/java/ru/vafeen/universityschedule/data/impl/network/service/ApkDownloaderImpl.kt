package ru.vafeen.universityschedule.data.impl.network.service

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.vafeen.universityschedule.domain.network.repository.DownloadFileRepository
import ru.vafeen.universityschedule.domain.network.service.ApkDownloader
import ru.vafeen.universityschedule.domain.network.service.ApkInstaller
import ru.vafeen.universityschedule.domain.utils.pathToDownloadRelease
import java.io.File
import java.io.FileOutputStream

internal class ApkDownloaderImpl(
    private val downloadFileRepository: DownloadFileRepository,
    private val apkInstaller: ApkInstaller,
) : ApkDownloader {
    private val _percentageFlow = MutableSharedFlow<Float>()
    override val percentageFlow = _percentageFlow.asSharedFlow()

    private val _isUpdateInProcessFlow = MutableSharedFlow<Boolean>()
    override val isUpdateInProcessFlow = _isUpdateInProcessFlow.asSharedFlow()


    override fun downloadApk(
        context: Context,
        url: String
    ) {
        Log.d("download", "url = $url")
        CoroutineScope(Dispatchers.IO).launch {
            _isUpdateInProcessFlow.emit(true)
        }
        val apkFilePath = context.pathToDownloadRelease()
        // Создаем вызов для загрузки файла
        val call = downloadFileRepository.downloadFile(url)
        // Выполняем асинхронный запрос
        call?.enqueue(object : Callback<ResponseBody> {
            // Обрабатываем успешный ответ
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val body = response.body()
                        // Проверяем, успешен ли ответ
                        if (response.isSuccessful && body != null) {
                            // Создаем файл для записи данных
                            val file = File(apkFilePath)
                            // Получаем поток данных из тела ответа
                            val inputStream = body.byteStream()
                            // Создаем поток для записи данных в файл
                            val outputStream = FileOutputStream(file)
                            // Буфер для чтения данных
                            val buffer = ByteArray(8 * 1024)
                            var bytesRead: Int
                            var totalBytesRead: Long = 0
                            // Получаем длину содержимого
                            val contentLength = body.contentLength()

                            // Используем потоки для чтения и записи данных
                            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                                // запись данных из буфера в выходной поток
                                outputStream.write(buffer, 0, bytesRead)
                                totalBytesRead += bytesRead
                                // Отправляем процент загрузки
                                _percentageFlow.emit(totalBytesRead.toFloat() / contentLength)
                                if (contentLength == totalBytesRead) {
                                    // отправляем окончание процесса загрузки
                                    _isUpdateInProcessFlow.emit(false)
                                    // установка
                                    apkInstaller.installApk(context = context)
                                }
                            }
                        } else {
                            //  Отправляем сигнал о неудаче
                            _isUpdateInProcessFlow.emit(false)
                            _percentageFlow.emit(0f)
                        }
                    } catch (e: Exception) {
                        // Обрабатываем исключение и отправляем сигнал о неудаче
                        _isUpdateInProcessFlow.emit(false)
                        _percentageFlow.emit(0f)
                    }
                }
            }

            // Обрабатываем ошибку при выполнении запроса
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("status", "Download error: ${t.message}")
            }
        })
    }

}