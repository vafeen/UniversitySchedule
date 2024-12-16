package ru.vafeen.universityschedule.data.utils

import com.google.gson.GsonBuilder
import ru.vafeen.universityschedule.data.database.entity.LessonEntity
import ru.vafeen.universityschedule.data.network.dto.googlesheets_service.ResponseWrapper
import java.time.DayOfWeek
import java.time.LocalTime
import java.util.Locale

/**
 * Удаляет подстроки из строки.
 *
 * @param substrings Подстроки, которые нужно удалить.
 * @return Строка без указанных подстрок.
 */
internal fun String.removeSubStrings(vararg substrings: String): String {
    var result = this
    substrings.forEach {
        result = result.replace(it, "")
    }
    return result
}

/**
 * Преобразует строку в день недели.
 *
 * @return [DayOfWeek] соответствующий дню недели.
 */
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

/**
 * Преобразует строку в целое число.
 *
 * @return Целое число, соответствующее строке, или 0 в случае ошибки.
 */
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

/**
 * Удаляет пробелы из строки.
 *
 * @return Строка без пробелов.
 */
internal fun String.removeSpaces() = this.removeSubStrings(" ")

/**
 * Преобразует строку времени урока в объект [LocalTime].
 *
 * @return [LocalTime] объект, представляющий время урока.
 */
internal fun String.toTimeOfLessonAsLocalTime(): LocalTime {
    val list = this.removeSubStrings(" ").split(":")
    return LocalTime.of(list[0].toMyInt(), list[1].toMyInt())
}

/**
 * Нормализует регистр строки, делая первую букву заглавной и все остальные строчными.
 *
 * @return Нормализованная строка.
 */
fun String.normalizeCase(): String = this.lowercase()
    .replaceFirstChar { it.titlecase(Locale.ROOT) }

/**
 * Преобразует JSON-строку в объект [ResponseWrapper].
 *
 * @return Объект [ResponseWrapper], полученный из JSON-строки.
 */
internal fun String.getResponseFromJson(): ResponseWrapper =
    GsonBuilder().setLenient().create().fromJson(this, ResponseWrapper::class.java)

/**
 * Извлекает JSON-строку из строки, содержащей ответ от Query.
 *
 * @return Извлеченная JSON-строка.
 */
internal fun String.dataToJsonString(): String = substringAfter("Query.setResponse(").let {
    it.substring(0, it.lastIndex - 1)
}

/**
 * Преобразует строку в null, если она содержит "null".
 *
 * @return null, если строка содержит "null", иначе оригинальная строка.
 */
internal fun String?.makeNullIfNull(): String? = if (this?.contains("null") == true) null else this

/**
 * Преобразует строку частоты в английский формат.
 *
 * @return Строка частоты на английском языке.
 */
internal fun String.toFrequencyString(): String = when (this.normalizeCase()) {
    "Числитель" -> "Numerator"
    "Знаменатель" -> "Denominator"
    else -> "Every"
}

/**
 * Получает список уроков из таблицы Google Sheets в виде объектов [LessonEntity].
 *
 * @return Список объектов [LessonEntity].
 */
internal fun String.getLessonsListFromGSheetsTable(): List<LessonEntity> =
    dataToJsonString() // Получаем данные как JSON
        .getResponseFromJson() // Получаем данные как класс Google Sheets из JSON
        .toLessonsList() // Получаем список уроков из класса Google Sheets
