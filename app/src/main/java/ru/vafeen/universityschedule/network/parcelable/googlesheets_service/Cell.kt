package ru.vafeen.universityschedule.network.parcelable.googlesheets_service

import com.google.gson.annotations.SerializedName

data class Cell(
    @SerializedName("v") val value: String?
) {
    override fun toString(): String = value.toString()
}