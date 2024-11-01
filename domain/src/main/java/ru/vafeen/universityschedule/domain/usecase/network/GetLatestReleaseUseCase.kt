package ru.vafeen.universityschedule.domain.usecase.network

import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.universityschedule.data.network.repository.NetworkRepository
import ru.vafeen.universityschedule.domain.converters.ReleaseConverter
import ru.vafeen.universityschedule.domain.database.models.Release

class GetLatestReleaseUseCase {
    private val networkRepository: NetworkRepository by inject(clazz = NetworkRepository::class.java)
    private val releaseConverter: ReleaseConverter by inject(clazz = ReleaseConverter::class.java)
    suspend operator fun invoke(): Release? = networkRepository.getLatestRelease()?.body()
        ?.let { releaseConverter.convertEntityDTO(it) }

}