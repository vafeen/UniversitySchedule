package ru.vafeen.universityschedule.domain.usecase.network

import ru.vafeen.universityschedule.domain.models.Lesson
import ru.vafeen.universityschedule.domain.network.NetworkRepository
import ru.vafeen.universityschedule.domain.network.end_points.GoogleSheetsServiceLink
import ru.vafeen.universityschedule.domain.usecase.base.UseCase

class GetSheetDataUseCase(private val networkRepository: NetworkRepository) : UseCase {
    suspend fun use(link: String): List<Lesson>? =
        networkRepository.getLessonsListFromGSheetsTable(
            "${
                link.substringBefore("/edit?")
                    .substringAfter(GoogleSheetsServiceLink.BASE_URL)
            }/${GoogleSheetsServiceLink.EndPoint.JSON}"
        )


}
