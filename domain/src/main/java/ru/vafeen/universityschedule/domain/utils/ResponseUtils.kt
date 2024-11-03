package ru.vafeen.universityschedule.data.utils

import ru.vafeen.universityschedule.data.database.entity.LessonEntity
import ru.vafeen.universityschedule.domain.network.parcelable.googlesheets_service.ResponseWrapper
import ru.vafeen.universityschedule.domain.network.parcelable.googlesheets_service.RowDTO

internal fun RowDTO.toLesson(): LessonEntity? = this.cellDTOS.map {
    it?.value
}.let {
    try {
        LessonEntity(
            dayOfWeek = it[0]?.toDayOfWeek(),
            name = it[1].makeNullIfNull(),
            startTime = "${it[2]}".toTimeOfLessonAsLocalTime(),
            endTime = "${it[3]}".toTimeOfLessonAsLocalTime(),
            classroom = it[4].makeNullIfNull(),
            teacher = it[5].makeNullIfNull(),
            subGroup = it[6].makeNullIfNull()?.normalizeCase(),
            frequency = it[7].makeNullIfNull()?.toFrequencyString()
        )
    } catch (e: Exception) {
        null
    }
}

internal fun ResponseWrapper.toLessonsList(): List<LessonEntity> {
    val result = mutableListOf<LessonEntity>()
    for (row in this.tableDTO.rowDTOS) {
        row.toLesson()?.let { result.add(element = it) }
    }
    return result
}





