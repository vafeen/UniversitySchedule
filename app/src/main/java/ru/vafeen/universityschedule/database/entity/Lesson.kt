package ru.vafeen.universityschedule.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.vafeen.universityschedule.noui.lesson_additions.Frequency
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
 * @param subGroup [name of Lesson subgroup]
 * @param frequency [frequency of Lesson: Numerator, Denominator, None]
 */
@Entity
data class Lesson(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dayOfWeek: DayOfWeek?,
    val name: String?,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val classroom: String?,
    val teacher: String?,
    val subGroup: String?,
    val frequency: Frequency?
) {
    override fun toString(): String {
        return "\n $dayOfWeek $name $startTime-$endTime $classroom $teacher $subGroup $frequency"
    }
}