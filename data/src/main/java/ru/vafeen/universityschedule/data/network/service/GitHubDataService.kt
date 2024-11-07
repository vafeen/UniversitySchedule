package ru.vafeen.universityschedule.data.network.service

import retrofit2.Response
import retrofit2.http.GET
import ru.vafeen.universityschedule.data.network.dto.github_service.ReleaseDTO
import ru.vafeen.universityschedule.domain.network.end_points.GitHubDataServiceLink

internal interface GitHubDataService {
    @GET(GitHubDataServiceLink.EndPoint.LATEST_RELEASE_INFO)
    suspend fun getLatestRelease(): Response<ReleaseDTO>
}