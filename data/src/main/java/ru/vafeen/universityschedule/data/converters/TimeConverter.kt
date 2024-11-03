package ru.vafeen.universityschedule.data.converters

import androidx.room.TypeConverter
import ru.vafeen.universityschedule.domain.converter.BaseConverter
import java.time.LocalTime

internal class TimeConverter : BaseConverter<Int?, LocalTime?> {

    @TypeConverter
    override fun convertAB(a: Int?): LocalTime? = a?.let { LocalTime.ofSecondOfDay(it.toLong()) }

    @TypeConverter
    override fun convertBA(b: LocalTime?): Int? = b?.toSecondOfDay()
}