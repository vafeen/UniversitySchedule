package ru.vafeen.universityschedule.database

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class DTConverters @Inject constructor() {
    @TypeConverter
    fun localDateTimeToLong(dateTime: LocalDateTime): Long {
        return dateTime.atZone(ZoneId.systemDefault()).toEpochSecond()
    }

    @TypeConverter
    fun longToLocalDateTime(timestamp: Long): LocalDateTime {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault())
    }
}