package ru.vafeen.universityschedule.noui.duration

enum class RepeatDuration(val duration: MyDuration) {
    EVERY_WEEK(duration = MyDuration.ofTime(days = 7)),
    EVERY_2_WEEKS(duration = MyDuration.ofTime(days = 14));
}