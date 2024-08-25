package ru.vafeen.universityschedule.network.downloader

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.FileProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.vafeen.universityschedule.network.repository.NetworkRepository
import java.io.File
import java.io.FileOutputStream


object Downloader {
    val sizeFlow = MutableSharedFlow<Progress>()
    val isUpdateInProcessFlow = MutableSharedFlow<Boolean>()
    fun installApk(context: Context, apkFilePath: String) {
        val file = File(apkFilePath)
        if (file.exists()) {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(
                    FileProvider.getUriForFile(
                        context,
                        "${context.packageName}.provider",
                        file
                    ),
                    "application/vnd.android.package-archive"
                )
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } else {
            Log.e("InstallApk", "APK file does not exist: $apkFilePath")
        }
    }

    fun downloadApk(
        networkRepository: NetworkRepository,
        url: String, filePath: String
    ) {
        val call = networkRepository.downloadFile(url)
        call?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
                    try {
                        if (response.isSuccessful) {
                            response.body()?.let { body ->
                                val file = File(filePath)
                                val inputStream = body.byteStream()
                                val outputStream = FileOutputStream(file)
                                val buffer = ByteArray(8 * 1024)
                                var bytesRead: Int
                                var totalBytesRead: Long = 0
                                val contentLength = body.contentLength()

                                inputStream.use { input ->
                                    outputStream.use { output ->
                                        while (input.read(buffer).also { bytesRead = it } != -1) {
                                            output.write(buffer, 0, bytesRead)
                                            totalBytesRead += bytesRead
                                            sizeFlow.emit(
                                                Progress(
                                                    totalBytesRead = totalBytesRead,
                                                    contentLength = contentLength,
                                                    done = totalBytesRead == contentLength
                                                )
                                            )
                                        }
                                    }
                                }
                                Log.d("status", "Downloaded")
                            }
                        } else {
                            Log.e("status", "Failed to download file")
                        }
                    } catch (e: Exception) {
                        CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
                            sizeFlow.emit(Progress(failed = true))
                        }
                    }
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("status", "Download error: ${t.message}")
            }
        })
    }
}