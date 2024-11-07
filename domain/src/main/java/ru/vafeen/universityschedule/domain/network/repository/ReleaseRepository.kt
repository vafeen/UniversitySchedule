package ru.vafeen.universityschedule.domain.network.repository

import ru.vafeen.universityschedule.domain.models.Release
import ru.vafeen.universityschedule.domain.network.result_status.latest_release.ReleaseResult

interface ReleaseRepository {
    suspend fun getLatestRelease(): ReleaseResult<Release>
}