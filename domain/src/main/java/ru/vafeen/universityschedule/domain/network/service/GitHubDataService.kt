package ru.vafeen.universityschedule.domain.network.service

import retrofit2.Response
import retrofit2.http.GET
import ru.vafeen.universityschedule.domain.models.Release
import ru.vafeen.universityschedule.domain.network.end_points.GitHubDataServiceLink

interface GitHubDataService {
    @GET(GitHubDataServiceLink.EndPoint.LATEST_RELEASE_INFO)
    suspend fun getLatestRelease(): Response<Release>
}