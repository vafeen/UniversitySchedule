package ru.vafeen.universityschedule.data.network.dto.googlesheets_service

import com.google.gson.annotations.SerializedName

internal data class RowDTO(
    @SerializedName("c") val cells: List<CellDTO?>
) {

    override fun toString(): String = "\n" + cells.toString()

}