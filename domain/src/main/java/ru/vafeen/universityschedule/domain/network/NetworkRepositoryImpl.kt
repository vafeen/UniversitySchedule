package ru.vafeen.universityschedule.domain.network

import android.util.Log
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Url
import ru.vafeen.universityschedule.data.database.entity.LessonEntity
import ru.vafeen.universityschedule.data.network.parcelable.github_service.ReleaseDTO
import ru.vafeen.universityschedule.data.network.repository.NetworkRepository
import ru.vafeen.universityschedule.data.network.service.DownloadService
import ru.vafeen.universityschedule.data.network.service.GSheetsService
import ru.vafeen.universityschedule.data.network.service.GitHubDataService
import ru.vafeen.universityschedule.data.utils.getLessonsListFromGSheetsTable

internal class NetworkRepositoryImpl(
    private val gitHubDataService: GitHubDataService,
    private val downloadService: DownloadService,
    private val gSheetsService: GSheetsService,
) : NetworkRepository {
    override suspend fun getLatestRelease(): Response<ReleaseDTO>? = try {
        Log.e("release", "начали2")
        gitHubDataService.getLatestRelease()
    } catch (e: Exception) {
        null
    }

    override fun downloadFile(@Url fileUrl: String): Call<ResponseBody>? = try {
        downloadService.downloadFile(fileUrl = fileUrl)
    } catch (e: Exception) {
        null
    }

    override suspend fun getLessonsListFromGSheetsTable(link: String): List<LessonEntity>? = try {
        gSheetsService.getLessonsListFromGSheetsTable(link = link)
    } catch (e: Exception) {
        null
    }

}

