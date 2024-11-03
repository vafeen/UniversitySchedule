package ru.vafeen.universityschedule.domain.converters

import androidx.room.TypeConverter
import ru.vafeen.universityschedule.domain.converters.base.BaseConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class DateTimeConverter : BaseConverter<LocalDateTime, Long> {

    @TypeConverter
    override fun convertAB(a: LocalDateTime): Long =
        a.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

    @TypeConverter
    override fun convertBA(b: Long): LocalDateTime =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(b), ZoneId.systemDefault())

}