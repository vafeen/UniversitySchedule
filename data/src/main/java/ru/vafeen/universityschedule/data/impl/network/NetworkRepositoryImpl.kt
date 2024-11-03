package ru.vafeen.universityschedule.data.impl.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Url
import ru.vafeen.universityschedule.data.converters.LessonConverter
import ru.vafeen.universityschedule.data.utils.getLessonsListFromGSheetsTable
import ru.vafeen.universityschedule.domain.models.Lesson
import ru.vafeen.universityschedule.domain.models.Release
import ru.vafeen.universityschedule.domain.network.NetworkRepository
import ru.vafeen.universityschedule.domain.network.service.DownloadService
import ru.vafeen.universityschedule.domain.network.service.GitHubDataService
import ru.vafeen.universityschedule.domain.network.service.GoogleSheetsService

internal class NetworkRepositoryImpl(
    private val gitHubDataService: GitHubDataService,
    private val downloadService: DownloadService,
    private val googleSheetsService: GoogleSheetsService,
    private val lessonConverter: LessonConverter,
) : NetworkRepository {
    override suspend fun getLatestRelease(): Response<Release>? = try {
        gitHubDataService.getLatestRelease()
    } catch (e: Exception) {
        null
    }

    override fun downloadFile(@Url fileUrl: String): Call<ResponseBody>? = try {
        downloadService.downloadFile(fileUrl = fileUrl)
    } catch (e: Exception) {
        null
    }

    override suspend fun getLessonsListFromGSheetsTable(link: String): List<Lesson>? = try {
        lessonConverter.convertABList(googleSheetsService.getLessonsListFromGSheetsTable(link = link))
    } catch (e: Exception) {
        null
    }

}

