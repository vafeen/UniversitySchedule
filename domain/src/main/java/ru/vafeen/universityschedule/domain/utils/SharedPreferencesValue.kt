package ru.vafeen.universityschedule.domain.utils

enum class SharedPreferencesValue(val key: String) {
    Name(key = "ScheduleSharedPreferences"),
    Settings(key = "Settings");
}