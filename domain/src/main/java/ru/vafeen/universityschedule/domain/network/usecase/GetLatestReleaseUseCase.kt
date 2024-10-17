package ru.vafeen.universityschedule.domain.network.usecase

import retrofit2.Response
import ru.vafeen.universityschedule.data.network.parcelable.github_service.Release
import ru.vafeen.universityschedule.data.network.service.GitHubDataService

class GetLatestReleaseUseCase(private val gitHubDataService: GitHubDataService) {
    suspend operator fun invoke(): Response<Release>? = try {
        gitHubDataService.getLatestRelease()
    } catch (_: Exception) {
        null
    }
}