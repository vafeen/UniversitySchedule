package ru.vafeen.universityschedule.domain.usecase.network

import ru.vafeen.universityschedule.domain.models.Release
import ru.vafeen.universityschedule.domain.network.repository.ReleaseRepository
import ru.vafeen.universityschedule.domain.network.result_status.latest_release.ErrorReleaseResult
import ru.vafeen.universityschedule.domain.network.result_status.latest_release.SuccessReleaseResult
import ru.vafeen.universityschedule.domain.usecase.base.UseCase

class GetLatestReleaseUseCase(private val releaseRepository: ReleaseRepository) :
    UseCase {
    suspend fun use(): Release? = releaseRepository.getLatestRelease().let {
        when (it) {
            is SuccessReleaseResult -> it.data
            is ErrorReleaseResult -> null
        }
    }
}