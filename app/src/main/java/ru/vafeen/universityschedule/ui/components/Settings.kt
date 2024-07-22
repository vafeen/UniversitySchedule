package ru.vafeen.universityschedule.ui.components

import androidx.compose.ui.graphics.Color
import com.google.gson.Gson

data class Settings(
    val lightThemeColor: Color? = null,
    val darkThemeColor: Color? = null,
    val subgroup: String? = null,
    val link: String? = null,
) {
    fun toJsonString(): String = Gson().toJson(this)
}
