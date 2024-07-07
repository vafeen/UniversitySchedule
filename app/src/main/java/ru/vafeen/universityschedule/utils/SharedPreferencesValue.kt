package ru.vafeen.universityschedule.utils

enum class SharedPreferencesValue(val key: String) {
    Name(key = "ScheduleSharedPreferences"),
    Link(key = "Link")
}