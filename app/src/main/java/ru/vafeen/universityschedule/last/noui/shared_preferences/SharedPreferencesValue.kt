package ru.vafeen.universityschedule.last.noui.shared_preferences

enum class SharedPreferencesValue(val key: String) {
    Name(key = "ScheduleSharedPreferences"),
    Settings(key = "Settings");
}