package ru.vafeen.universityschedule.data.network.parcelable.googlesheets_service

import com.google.gson.annotations.SerializedName

data class Cell(
    @SerializedName("v") val value: String?
) {
    override fun toString(): String = value.toString()
}