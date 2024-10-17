package ru.vafeen.universityschedule.data.network.service

import retrofit2.Response
import retrofit2.http.GET
import ru.vafeen.universityschedule.data.network.end_points.GHDServiceLink
import ru.vafeen.universityschedule.data.network.parcelable.github_service.Release

interface GitHubDataService {
    @GET(GHDServiceLink.EndPoint.LATEST_RELEASE_INFO)
    suspend fun getLatestRelease(): Response<Release>
}