package ru.vafeen.universityschedule.data.network.dto.googlesheets_service

import com.google.gson.annotations.SerializedName

internal data class CellDTO(
    @SerializedName("v") val value: String?
) {
    override fun toString(): String = value.toString()
}