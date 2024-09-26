package ru.vafeen.universityschedule.noui.shared_preferences

enum class SharedPreferencesValue(val key: String) {
    Name(key = "ScheduleSharedPreferences"),
    Settings(key = "Settings");
}