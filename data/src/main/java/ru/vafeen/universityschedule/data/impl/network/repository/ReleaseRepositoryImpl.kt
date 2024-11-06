package ru.vafeen.universityschedule.data.impl.network.repository

import retrofit2.Response
import ru.vafeen.universityschedule.domain.models.Release
import ru.vafeen.universityschedule.domain.network.repository.ReleaseRepository
import ru.vafeen.universityschedule.domain.network.service.GitHubDataService

internal class ReleaseRepositoryImpl(private val gitHubDataService: GitHubDataService) :
    ReleaseRepository {
    override suspend fun getLatestRelease(): Response<Release>? = try {
        gitHubDataService.getLatestRelease()
    } catch (e: Exception) {
        null
    }
}