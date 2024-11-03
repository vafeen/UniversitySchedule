package ru.vafeen.universityschedule.domain.utils

import ru.vafeen.universityschedule.data.database.entity.LessonEntity
import ru.vafeen.universityschedule.data.utils.dataToJsonString
import ru.vafeen.universityschedule.data.utils.getResponseFromJson
import ru.vafeen.universityschedule.data.utils.toLessonsList
import ru.vafeen.universityschedule.domain.network.service.GoogleSheetsService

suspend fun GoogleSheetsService.getLessonsListFromGSheetsTable(link: String): List<LessonEntity> =
//   get data as non-parcelable string
    getSheetData(link = link).string()
//     get data as Json
        .dataToJsonString()
//     get data as class of Google Sheets from Json
        .getResponseFromJson()
//     get lessons list from class Google Sheets
        .toLessonsList()

