package ru.vafeen.universityschedule.domain.network.parcelable.googlesheets_service

import com.google.gson.annotations.SerializedName

data class RowDTO(
    @SerializedName("c") val cellDTOS: List<CellDTO?>
) {

    override fun toString(): String = "\n" + cellDTOS.toString()

}