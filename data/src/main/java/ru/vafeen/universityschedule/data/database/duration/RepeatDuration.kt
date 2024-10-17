package ru.vafeen.universityschedule.data.database.duration

import ru.vafeen.universityschedule.data.R

enum class RepeatDuration(val duration: MyDuration, val resourceName: Int) {
    EVERY_WEEK(duration = MyDuration.ofTime(days = 7), resourceName = R.string.every_week),
    EVERY_2_WEEKS(duration = MyDuration.ofTime(days = 14), resourceName = R.string.every_2_weeks);
}