package ru.vafeen.universityschedule.network.parcelable

import com.google.gson.annotations.SerializedName

data class Cell(
    @SerializedName("v") val value: String
)