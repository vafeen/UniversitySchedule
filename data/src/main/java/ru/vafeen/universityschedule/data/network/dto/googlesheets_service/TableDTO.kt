package ru.vafeen.universityschedule.data.network.dto.googlesheets_service

import com.google.gson.annotations.SerializedName

internal data class TableDTO(
    @SerializedName("cols") val cols: List<ColumnDTO>,
    @SerializedName("rows") val rows: List<RowDTO>,
    @SerializedName("parsedNumHeaders") val parsedNumHeaders: Int
) {
    override fun toString(): String = rows.toString()
}