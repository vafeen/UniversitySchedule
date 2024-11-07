package ru.vafeen.universityschedule.data.impl.network.repository

import ru.vafeen.universityschedule.data.converters.ReleaseConverter
import ru.vafeen.universityschedule.data.network.dto.github_service.ReleaseDTO
import ru.vafeen.universityschedule.domain.models.Release
import ru.vafeen.universityschedule.domain.network.repository.ReleaseRepository
import ru.vafeen.universityschedule.domain.network.result_status.latest_release.ErrorReleaseResult
import ru.vafeen.universityschedule.domain.network.result_status.latest_release.ReleaseResult
import ru.vafeen.universityschedule.domain.network.result_status.latest_release.SuccessReleaseResult
import ru.vafeen.universityschedule.data.network.service.GitHubDataService

internal class ReleaseRepositoryImpl(
    private val gitHubDataService: GitHubDataService,
    private val releaseConverter: ReleaseConverter
) :
    ReleaseRepository {
    override suspend fun getLatestRelease(): ReleaseResult<Release> = try {
            SuccessReleaseResult(
                data = releaseConverter.convertAB(
                    gitHubDataService.getLatestRelease().body() as ReleaseDTO
                )
            )
    } catch (e: Exception) {
        ErrorReleaseResult(exception = e)
    }
}