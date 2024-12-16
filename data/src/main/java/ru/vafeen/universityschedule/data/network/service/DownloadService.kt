package ru.vafeen.universityschedule.data.network.service

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * Интерфейс для определения методов API, связанных с загрузкой файлов.
 */
internal interface DownloadService {

    /**
     * Загружает файл по указанному URL.
     *
     * @param fileUrl URL файла, который нужно загрузить.
     * @return [Call] для выполнения запроса и получения [ResponseBody].
     */
    @GET
    @Streaming // Используется для загрузки больших файлов, чтобы избежать переполнения памяти
    fun downloadFile(@Url fileUrl: String): Call<ResponseBody>
}
