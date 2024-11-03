package ru.vafeen.universityschedule.domain.network.parcelable.googlesheets_service

import com.google.gson.annotations.SerializedName

/**
 * Class into which the data from GSheets is parsed
 */
data class ResponseWrapper(
    @SerializedName("version") val version: String,
    @SerializedName("reqId") val reqId: String,
    @SerializedName("status") val status: String,
    @SerializedName("sig") val sig: String,
    @SerializedName("table") val tableDTO: TableDTO
) {
    override fun toString(): String = tableDTO.toString()
}