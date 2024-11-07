package ru.vafeen.universityschedule.domain.models

import androidx.compose.ui.graphics.Color
import com.google.gson.Gson

data class Settings(
    val lightThemeColor: Color? = null,
    val darkThemeColor: Color? = null,
    val subgroup: String? = null,
    val link: String? = null,
    val isSelectedFrequencyCorrespondsToTheWeekNumbers: Boolean? = null,
    val lastDemonstratedVersion: Int? = null,
    val weekendCat: Boolean = false,
    val catInSettings: Boolean = false,
    val notesAboutLesson: Boolean = false,
    val notificationsAboutLesson: Boolean = true,
) {
    fun toJsonString(): String = Gson().toJson(this)
}
