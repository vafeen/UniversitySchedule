package ru.vafeen.universityschedule.domain

import androidx.compose.ui.graphics.Color
import com.google.gson.Gson

data class Settings(
    val lightThemeColor: Color? = null,
    val darkThemeColor: Color? = null,
    val subgroup: String? = null,
    val link: String? = null,
    val isSelectedFrequencyCorrespondsToTheWeekNumbers: Boolean? = null,
    val lastDemonstratedVersion: Int? = null,
) {
    fun toJsonString(): String = Gson().toJson(this)
    override fun toString(): String {
        return "isSelectedFrequencyCorrespondsToTheWeekNumbers= $isSelectedFrequencyCorrespondsToTheWeekNumbers"
    }
}
