package ru.vafeen.universityschedule.presentation.utils

import android.content.Context
import ru.vafeen.universityschedule.domain.models.model_additions.Frequency
import ru.vafeen.universityschedule.resources.R
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Month
import java.time.temporal.ChronoField

// Список строковых ресурсов для дней недели
private val daysOfWeek = listOf(
    R.string.monday,
    R.string.tuesday,
    R.string.wednesday,
    R.string.thursday,
    R.string.friday,
    R.string.satudray,
    R.string.sunday
)

/**
 * Оператор сравнения для сравнения времени (LocalTime) с LocalDateTime.
 * Сравнивает только время, игнорируя дату.
 *
 * @param localDateTime Дата и время для сравнения с текущим временем.
 * @return Результат сравнения текущего времени с временем из LocalDateTime.
 */
internal operator fun LocalTime.compareTo(localDateTime: LocalDateTime): Int {
    return this.compareTo(LocalTime.of(localDateTime.hour, localDateTime.minute))
}

/**
 * Функция для получения строки с датой и днем недели.
 * Возвращает строку вида "Понедельник, 01.09", где день недели берется из списка daysOfWeek,
 * а месяц всегда отображается с ведущим нулем, если его номер меньше 10.
 *
 * @param context Контекст для получения строкового ресурса дня недели.
 * @return Строка, содержащая день недели и дату.
 */
internal fun LocalDate.getDateStringWithWeekOfDay(context: Context): String =
    "${context.getString(daysOfWeek[dayOfWeek.value - 1])}, ${dayOfMonth}." + if (month.value < 10) "0${month.value}"
    else month.value

/**
 * Функция для получения даты последнего сентября текущего учебного года.
 * Если текущая дата позже 1 сентября, то возвращается 1 сентября этого года.
 * Если текущая дата до 1 сентября, то возвращается 1 сентября предыдущего года.
 *
 * @return Дата 1 сентября текущего учебного года.
 */
internal fun LocalDate.getLastSeptemberOfThisAcademicYear(): LocalDate {
    return if (this >= LocalDate.of(year, Month.SEPTEMBER, 1))
        LocalDate.of(year, Month.SEPTEMBER, 1)
    else LocalDate.of(year - 1, Month.SEPTEMBER, 1)
}

/**
 * Функция для определения частоты (Числитель / Знаменатель) по текущей дате.
 * Определяет четность недели и возвращает соответствующую частоту.
 * Если 1 сентября выпало на воскресенье, то частота инвертируется.
 *
 * @return Возвращает частоту для текущей даты.
 */
fun LocalDate.getFrequencyByLocalDate(): Frequency {
    val lastSeptember = getLastSeptemberOfThisAcademicYear()
    val resultFrequency =
        if (get(ChronoField.ALIGNED_WEEK_OF_YEAR) % 2 == 0) Frequency.Denominator
        else Frequency.Numerator
    return if (lastSeptember.dayOfWeek == DayOfWeek.SUNDAY)
        resultFrequency.getOpposite()
    else resultFrequency
}

/**
 * Функция для преобразования времени (LocalTime) в строку формата "часы:минуты".
 * Если минуты меньше 10, то добавляется ведущий ноль.
 *
 * @return Строка с временем в формате "часы:минуты".
 */
fun LocalTime.toLessonTime(): String = "${hour}:" + if (minute < 10) "0${minute}" else "$minute"
