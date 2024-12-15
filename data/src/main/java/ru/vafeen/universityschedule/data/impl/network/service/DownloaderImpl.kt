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

internal class DownloaderImpl(
    private val context: Context,
    private val downloadFileRepository: DownloadFileRepository,
    private val installer: Installer,
) : Downloader {
    private val _percentageFlow = MutableSharedFlow<Float>()
    override val percentageFlow = _percentageFlow.asSharedFlow()

    private val _isUpdateInProcessFlow = MutableSharedFlow<Boolean>()
    override val isUpdateInProcessFlow = _isUpdateInProcessFlow.asSharedFlow()

    override fun downloadApk(url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            _isUpdateInProcessFlow.emit(true)
            val apkFilePath = context.pathToDownloadRelease()
            when (val result = downloadFileRepository.downloadFile(url)) {
                is ResponseResult.Error -> {
                    _isUpdateInProcessFlow.emit(false)
                    _percentageFlow.emit(0f)
                }

                is ResponseResult.Success -> {
                    val file = File(apkFilePath)
                    val inputStream = result.data.stream
                    val outputStream = FileOutputStream(file)
                    val buffer = ByteArray(8 * 1024)
                    var bytesRead: Int
                    var totalBytesRead: Long = 0
                    val contentLength = result.data.contentLength

                    while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                        outputStream.write(buffer, 0, bytesRead)
                        totalBytesRead += bytesRead
                        _percentageFlow.emit(totalBytesRead.toFloat() / contentLength)
                    }

                    outputStream.close()
                    inputStream.close()
                    _isUpdateInProcessFlow.emit(false)
                    installer.installApk()
                }
            }
        }
    }
}