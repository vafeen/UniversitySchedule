package ru.vafeen.universityschedule.database

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class DTConverters {

    @TypeConverter
    fun localDateTimeToLongMilliSeconds(dateTime: LocalDateTime): Long =
        dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()


    @TypeConverter
    fun longMilliSecondsToLocalDateTime(timestamp: Long): LocalDateTime =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())

}