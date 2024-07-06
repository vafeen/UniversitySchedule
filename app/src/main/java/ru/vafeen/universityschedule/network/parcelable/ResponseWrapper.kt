package ru.vafeen.universityschedule.network.parcelable

import com.google.gson.annotations.SerializedName

data class ResponseWrapper(
    @SerializedName("version") val version: String,
    @SerializedName("reqId") val reqId: String,
    @SerializedName("status") val status: String,
    @SerializedName("sig") val sig: String,
    @SerializedName("table") val table: Table
)