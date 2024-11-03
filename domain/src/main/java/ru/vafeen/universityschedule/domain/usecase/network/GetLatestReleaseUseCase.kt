package ru.vafeen.universityschedule.domain.usecase.network

import ru.vafeen.universityschedule.domain.models.Release
import ru.vafeen.universityschedule.domain.network.NetworkRepository
import ru.vafeen.universityschedule.domain.usecase.base.UseCase

class GetLatestReleaseUseCase(private val networkRepository: NetworkRepository) : UseCase {
    suspend fun use(): Release? = networkRepository.getLatestRelease()?.body()
}