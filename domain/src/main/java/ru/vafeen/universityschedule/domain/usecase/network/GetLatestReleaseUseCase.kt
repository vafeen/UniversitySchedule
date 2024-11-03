package ru.vafeen.universityschedule.domain.usecase.network

import android.util.Log
import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.universityschedule.domain.converters.ReleaseConverter
import ru.vafeen.universityschedule.domain.models.Release
import ru.vafeen.universityschedule.domain.network.repository.NetworkRepository

class GetLatestReleaseUseCase {
    private val networkRepository: NetworkRepository by inject(clazz = NetworkRepository::class.java)
    private val releaseConverter: ReleaseConverter by inject(clazz = ReleaseConverter::class.java)
    suspend fun use(): Release? = networkRepository.getLatestRelease()?.body()
        ?.let {
            Log.d("release", "$it")
            releaseConverter.convertAB(it) }

}