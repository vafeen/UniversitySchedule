package ru.vafeen.universityschedule.domain.network.service

import ru.vafeen.universityschedule.domain.models.Release
import ru.vafeen.universityschedule.domain.network.result.ResponseResult

interface ReleaseRepository {
    suspend fun getLatestRelease(): ResponseResult<Release>
}