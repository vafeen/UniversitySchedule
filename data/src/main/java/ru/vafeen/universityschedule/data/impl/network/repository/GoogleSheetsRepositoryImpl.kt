package ru.vafeen.universityschedule.data.impl.network.repository

import retrofit2.http.Url
import ru.vafeen.universityschedule.data.converters.LessonConverter
import ru.vafeen.universityschedule.data.utils.getLessonsListFromGSheetsTable
import ru.vafeen.universityschedule.domain.models.Lesson
import ru.vafeen.universityschedule.domain.network.repository.GoogleSheetsRepository
import ru.vafeen.universityschedule.domain.network.service.GoogleSheetsService

internal class GoogleSheetsRepositoryImpl(
    private val googleSheetsService: GoogleSheetsService,
    private val lessonConverter: LessonConverter,
) : GoogleSheetsRepository {
    override suspend fun getLessonsListFromGSheetsTable(@Url link: String): List<Lesson>? = try {
        lessonConverter.convertABList(googleSheetsService.getLessonsListFromGSheetsTable(link = link))
    } catch (e: Exception) {
        null
    }
}