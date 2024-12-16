package ru.vafeen.universityschedule.domain.network.service

import ru.vafeen.universityschedule.domain.models.Release
import ru.vafeen.universityschedule.domain.network.result.ResponseResult

/**
 * Интерфейс репозитория для работы с релизами.
 */
interface ReleaseRepository {

    /**
     * Получает информацию о последнем релизе.
     *
     * @return [ResponseResult] с результатом запроса, содержащим объект [Release].
     */
    suspend fun getLatestRelease(): ResponseResult<Release>
}
