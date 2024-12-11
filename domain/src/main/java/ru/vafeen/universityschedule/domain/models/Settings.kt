package ru.vafeen.universityschedule.domain.models

import androidx.compose.ui.graphics.Color
import com.google.gson.Gson

data class Settings(
    var lightThemeColor: Color? = null,
    var darkThemeColor: Color? = null,
    var subgroup: String? = null,
    var link: String? = null,
    var isSelectedFrequencyCorrespondsToTheWeekNumbers: Boolean? = null,
    var lastDemonstratedVersion: Long = 1,
    var weekendCat: Boolean = false,
    var catInSettings: Boolean = false,
    var notesAboutLesson: Boolean = false,
    var notificationsAboutLesson: Boolean = true,
    var releaseBody: String = "",
    var isMigrationFromAlarmManagerToWorkManagerSuccessful: Boolean = false
) {
    fun toJsonString(): String = Gson().toJson(this)
}
