package ru.vafeen.universityschedule.data.utils

import com.google.gson.GsonBuilder
import ru.vafeen.universityschedule.data.database.lesson_additions.Frequency
import ru.vafeen.universityschedule.data.network.parcelable.googlesheets_service.ResponseWrapper
import java.time.DayOfWeek
import java.time.LocalTime
import java.util.Locale

internal fun String.removeSubStrings(vararg substrings: String): String {
    var result = this
    substrings.forEach {
        result = result.replace(it, "")
    }
    return result
}

internal fun String.toDayOfWeek(): DayOfWeek {
    return when (this.normalizeCase().removeSpaces()) {
        "Понедельник" -> DayOfWeek.MONDAY
        "Вторник" -> DayOfWeek.TUESDAY
        "Среда" -> DayOfWeek.WEDNESDAY
        "Четверг" -> DayOfWeek.THURSDAY
        "Пятница" -> DayOfWeek.FRIDAY
        "Суббота" -> DayOfWeek.SATURDAY
        else -> DayOfWeek.SUNDAY
    }
}

internal fun String.toMyInt(): Int {
    val thisWithoutSpaces = this.removeSpaces()
    try {
        return thisWithoutSpaces.toInt()
    } catch (e: Exception) {
        return when (thisWithoutSpaces) {
            "01" -> 1
            "02" -> 2
            "03" -> 3
            "04" -> 4
            "05" -> 5
            "06" -> 6
            "07" -> 7
            "08" -> 8
            "09" -> 9
            else -> 0
        }
    }

}

internal fun String.removeSpaces() = this.removeSubStrings(" ")

internal fun String.toTimeOfLessonAsLocalTime(): LocalTime {
    val list = this.removeSubStrings(" ").split(":")
    return LocalTime.of(list[0].toMyInt(), list[1].toMyInt())
}

internal fun String.toFrequency(): Frequency = when (this.normalizeCase()) {
    "Числитель" -> Frequency.Numerator
    "Знаменатель" -> Frequency.Denominator
    else -> Frequency.Every
}

internal fun String.normalizeCase(): String = this.lowercase()
    .replaceFirstChar { it.titlecase(Locale.ROOT) }

internal fun String.getResponseFromJson(): ResponseWrapper =
    GsonBuilder().setLenient().create().fromJson(this, ResponseWrapper::class.java)

internal fun String.dataToJsonString(): String = substringAfter("Query.setResponse(").let {
    it.substring(0, it.lastIndex - 1)
}

internal fun String?.makeNullIfNull(): String? = if (this?.contains("null") == true) null else this