package ru.vafeen.universityschedule.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.DayOfWeek
import java.time.LocalTime

/**
 * Entity of one Lesson
 * @param dayOfWeek [day of week of this Lesson]
 * @param name [name of Lesson]
 * @param startTime [start time of Lesson]
 * @param endTime [end time of Lesson]
 * @param classroom [classroom of Lesson]
 * @param teacher [name of teacher of Lesson]
 * @param subGroup [number of subgroup of Lesson: 1 or 2]
 * @param frequency [frequency of Lesson: Numerator, Denominator, Every]
 *
 */
@Entity
data class Lesson(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val dayOfWeek: DayOfWeek,
    val name: String,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val classroom: String,
    val teacher: String,
    val subGroup: Int,
    val frequency: String
) {
}