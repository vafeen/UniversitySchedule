package ru.vafeen.universityschedule.domain.usecase.network

import ru.vafeen.universityschedule.domain.models.Lesson
import ru.vafeen.universityschedule.domain.network.end_points.GoogleSheetsServiceLink
import ru.vafeen.universityschedule.domain.network.repository.SheetDataRepository
import ru.vafeen.universityschedule.domain.network.result_status.sheet_data.SheetDataResult
import ru.vafeen.universityschedule.domain.usecase.base.UseCase

class GetSheetDataUseCase(private val sheetDataRepository: SheetDataRepository) : UseCase {
    suspend fun use(link: String): SheetDataResult<List<Lesson>> =
        sheetDataRepository.getLessonsListFromGSheetsTable(
            "${
                link.substringBefore("/edit?")
                    .substringAfter(GoogleSheetsServiceLink.BASE_URL)
            }/${GoogleSheetsServiceLink.EndPoint.JSON}"
        )


}
