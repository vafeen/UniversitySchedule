package ru.vafeen.universityschedule.network.parcelable

import com.google.gson.annotations.SerializedName

data class Row(
    @SerializedName("c") val cells: List<Cell?>
) {

    override fun toString(): String = "\n" + cells.toString()

}