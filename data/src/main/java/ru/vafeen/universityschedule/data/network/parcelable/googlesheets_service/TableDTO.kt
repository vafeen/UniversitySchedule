package ru.vafeen.universityschedule.data.network.parcelable.googlesheets_service

import com.google.gson.annotations.SerializedName

data class TableDTO(
    @SerializedName("cols") val cols: List<ColumnDTO>,
    @SerializedName("rows") val rowDTOS: List<RowDTO>,
    @SerializedName("parsedNumHeaders") val parsedNumHeaders: Int
) {
    override fun toString(): String = rowDTOS.toString()
}