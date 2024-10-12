package ru.vafeen.universityschedule.noui.duration

enum class RepeatDuration(val duration: MyDuration) {
    NO_REPEAT(duration = MyDuration(0)),
    EVERY_WEEK(duration = MyDuration.ofTime(days = 7)),
    EVERY_2_WEEKS(duration = MyDuration.ofTime(days = 14));
}