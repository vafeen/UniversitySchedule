package ru.vafeen.universityschedule.domain.network.parcelable.googlesheets_service

import com.google.gson.annotations.SerializedName

data class CellDTO(
    @SerializedName("v") val value: String?
) {
    override fun toString(): String = value.toString()
}