package ru.vafeen.universityschedule.data.converters

import androidx.room.TypeConverter
import ru.vafeen.universityschedule.domain.converter.BaseConverter
import java.time.LocalTime

/**
 * Конвертер для преобразования [Int?] в [LocalTime?] и обратно.
 *
 * Используется для сохранения времени в базе данных в формате количества секунд с начала суток
 * и его преобразования в объект [LocalTime] при работе с доменной моделью.
 */
internal class TimeConverter : BaseConverter<Int?, LocalTime?> {

    /**
     * Преобразует [Int?] в [LocalTime?].
     *
     * @param a Количество секунд с начала суток или null.
     * @return Экземпляр [LocalTime], соответствующий переданным секундам, или null.
     */
    @TypeConverter
    override fun convertAB(a: Int?): LocalTime? = a?.let { LocalTime.ofSecondOfDay(it.toLong()) }

    /**
     * Преобразует [LocalTime?] в [Int?].
     *
     * @param b Экземпляр [LocalTime] или null.
     * @return Количество секунд с начала суток или null, если входное значение равно null.
     */
    @TypeConverter
    override fun convertBA(b: LocalTime?): Int? = b?.toSecondOfDay()
}