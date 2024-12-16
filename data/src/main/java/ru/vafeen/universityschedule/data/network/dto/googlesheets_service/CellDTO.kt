package ru.vafeen.universityschedule.data.network.dto.googlesheets_service

import com.google.gson.annotations.SerializedName

/**
 * Класс, представляющий ячейку таблицы из данных Google Sheets.
 *
 * @property value Значение, содержащееся в ячейке. Может быть null, если ячейка пуста.
 */
internal data class CellDTO(
    @SerializedName("v") val value: String? // Значение ячейки
) {
    override fun toString(): String =
        value.toString() // Переопределение метода toString для удобства отображения значения ячейки
}
