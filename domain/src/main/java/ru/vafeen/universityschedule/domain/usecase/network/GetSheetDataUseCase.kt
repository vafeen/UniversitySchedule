package ru.vafeen.universityschedule.domain.usecase.network

import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.universityschedule.data.database.entity.LessonEntity
import ru.vafeen.universityschedule.domain.network.end_points.GoogleSheetsServiceLink
import ru.vafeen.universityschedule.domain.network.repository.NetworkRepository

class GetSheetDataUseCase {
    private val networkRepository: NetworkRepository by inject(clazz = NetworkRepository::class.java)


    suspend fun use(link: String): List<LessonEntity>? =
        networkRepository.getLessonsListFromGSheetsTable(
            "${
                link.substringBefore("/edit?")
                    .substringAfter(GoogleSheetsServiceLink.BASE_URL)
            }/${GoogleSheetsServiceLink.EndPoint.JSON}"
        )


}
