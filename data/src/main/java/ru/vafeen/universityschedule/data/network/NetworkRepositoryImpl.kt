package ru.vafeen.universityschedule.data.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Url
import ru.vafeen.universityschedule.data.database.entity.LessonEntity
import ru.vafeen.universityschedule.domain.network.parcelable.github_service.ReleaseDTO
import ru.vafeen.universityschedule.domain.network.repository.NetworkRepository
import ru.vafeen.universityschedule.domain.network.service.DownloadService
import ru.vafeen.universityschedule.domain.network.service.GitHubDataService
import ru.vafeen.universityschedule.domain.network.service.GoogleSheetsService
import ru.vafeen.universityschedule.domain.utils.getLessonsListFromGSheetsTable

internal class NetworkRepositoryImpl(
    private val gitHubDataService: GitHubDataService,
    private val downloadService: DownloadService,
    private val googleSheetsService: GoogleSheetsService,
) : NetworkRepository {
    override suspend fun getLatestRelease(): Response<ReleaseDTO>? = try {
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
        googleSheetsService.getLessonsListFromGSheetsTable(link = link)
    } catch (e: Exception) {
        null
    }

}

