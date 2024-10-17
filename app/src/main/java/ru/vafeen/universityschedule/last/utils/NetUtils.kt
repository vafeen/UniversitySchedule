package ru.vafeen.universityschedule.last.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import ru.vafeen.universityschedule.last.database.entity.Lesson
import ru.vafeen.universityschedule.last.network.service.GSheetsService

fun openLink(context: Context, link: String) {
    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
}

suspend fun GSheetsService.getLessonsListFromGSheetsTable(): List<Lesson> =
//   get data as non-parcelable string
    getSheetData().string()
//     get data as Json
        .dataToJsonString()
//     get data as class of Google Sheets from Json
        .getResponseFromJson()
//     get lessons list from class Google Sheets
        .toLessonsList()

