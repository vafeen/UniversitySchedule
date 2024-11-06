package ru.vafeen.universityschedule.domain.network.repository

import retrofit2.Response
import ru.vafeen.universityschedule.domain.models.Release

interface ReleaseRepository {
    suspend fun getLatestRelease(): Response<Release>?
}