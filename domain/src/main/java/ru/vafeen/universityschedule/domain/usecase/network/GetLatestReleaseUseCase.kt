package ru.vafeen.universityschedule.domain.usecase.network

import ru.vafeen.universityschedule.domain.models.Release
import ru.vafeen.universityschedule.domain.network.result.ResponseResult
import ru.vafeen.universityschedule.domain.network.service.ReleaseRepository
import ru.vafeen.universityschedule.domain.usecase.base.UseCase

class GetLatestReleaseUseCase(private val releaseRepository: ReleaseRepository) :
    UseCase {
    suspend fun use(): Release? = releaseRepository.getLatestRelease().let {
        when (it) {
            is ResponseResult.Success -> it.data
            is ResponseResult.Error -> null
        }
    }
}