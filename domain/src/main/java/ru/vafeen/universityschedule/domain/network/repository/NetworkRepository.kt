package ru.vafeen.universityschedule.domain.network.repository

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Url
import ru.vafeen.universityschedule.data.database.entity.LessonEntity
import ru.vafeen.universityschedule.domain.network.parcelable.github_service.ReleaseDTO


interface NetworkRepository {
    suspend fun getLatestRelease(): Response<ReleaseDTO>?
    fun downloadFile(@Url fileUrl: String): Call<ResponseBody>?
    suspend fun getLessonsListFromGSheetsTable(@Url link: String): List<LessonEntity>?
}