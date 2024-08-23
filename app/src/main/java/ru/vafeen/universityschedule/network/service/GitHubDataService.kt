package ru.vafeen.universityschedule.network.service

import retrofit2.Response
import retrofit2.http.GET
import ru.vafeen.universityschedule.network.parcelable.github_service.Release

interface GitHubDataService {
    @GET("repos/vafeen/UniversitySchedule/releases/latest")
    suspend fun getLatestRelease(): Response<Release>
}