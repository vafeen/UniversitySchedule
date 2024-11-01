package ru.vafeen.universityschedule.domain.usecase.network

import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.universityschedule.data.database.entity.LessonEntity
import ru.vafeen.universityschedule.data.network.repository.NetworkRepository
import ru.vafeen.universityschedule.domain.GSheetsServiceLink

class GetSheetDataUseCase {
    private val networkRepository: NetworkRepository by inject(clazz = NetworkRepository::class.java)


    suspend operator fun invoke(link: String): List<LessonEntity>? =
        networkRepository.getLessonsListFromGSheetsTable(
            "${
                link.substringBefore("/edit?")
                    .substringAfter(GSheetsServiceLink.BASE_URL)
            }/${GSheetsServiceLink.EndPoint.JSON}"
        )


}
