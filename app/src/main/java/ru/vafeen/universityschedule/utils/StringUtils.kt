package ru.vafeen.universityschedule.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.google.gson.GsonBuilder
import ru.vafeen.universityschedule.network.parcelable.googlesheets_service.ResponseWrapper
import ru.vafeen.universityschedule.noui.lesson_additions.Frequency
import java.time.DayOfWeek
import java.time.LocalTime
import java.util.Locale

fun String.removeSubStrings(vararg substrings: String): String {
    var result = this
    substrings.forEach {
        result = result.replace(it, "")
    }
    return result
}

fun Context.copyTextToClipBoard(text: String) {
    val clipboard =
        getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label", text)
    if (text.isNotEmpty()) clipboard.setPrimaryClip(clip)
}

fun Context.pasteText(): String? {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    return clipboard.primaryClip?.getItemAt(0)?.text?.toString()
}

fun String.toDayOfWeek(): DayOfWeek {
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

fun String.toMyInt(): Int {
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

fun String.removeSpaces() = this.removeSubStrings(" ")

fun String.toTimeOfLessonAsLocalTime(): LocalTime {
    val list = this.removeSubStrings(" ").split(":")
    return LocalTime.of(list[0].toMyInt(), list[1].toMyInt())
}

fun String.toFrequency(): Frequency = when (this.normalizeCase()) {
    "Числитель" -> Frequency.Numerator
    "Знаменатель" -> Frequency.Denominator
    else -> Frequency.Every
}

fun String.normalizeCase(): String = this.lowercase()
    .replaceFirstChar { it.titlecase(Locale.ROOT) }

fun String.getResponseFromJson(): ResponseWrapper =
    GsonBuilder().setLenient().create().fromJson(this, ResponseWrapper::class.java)

fun String.dataToJsonString(): String = substringAfter("Query.setResponse(").let {
    it.substring(0, it.lastIndex - 1)
}

fun String?.makeNullIfNull(): String? = if (this?.contains("null") == true) null else this