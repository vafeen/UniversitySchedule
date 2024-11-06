package ru.vafeen.universityschedule.data.converters

import androidx.room.TypeConverter
import ru.vafeen.universityschedule.domain.converter.BaseConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

internal class DateTimeConverter : BaseConverter<LocalDateTime, Long> {

    @TypeConverter
    override fun convertAB(a: LocalDateTime): Long =
        a.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

    @TypeConverter
    override fun convertBA(b: Long): LocalDateTime =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(b), ZoneId.systemDefault())

}