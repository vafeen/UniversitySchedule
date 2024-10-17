package ru.vafeen.universityschedule.domain.shared_preferences

internal enum class SharedPreferencesValue(val key: String) {
    Name(key = "ScheduleSharedPreferences"),
    Settings(key = "Settings");
}