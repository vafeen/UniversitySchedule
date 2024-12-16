package ru.vafeen.universityschedule.data.network.dto.googlesheets_service

import com.google.gson.annotations.SerializedName

/**
 * Класс, представляющий строку таблицы из данных Google Sheets.
 *
 * @property cells Список ячеек (CellDTO) в строке.
 */
internal data class RowDTO(
    @SerializedName("c") val cells: List<CellDTO?> // Список ячеек, представляющих данные в строке
) {
    override fun toString(): String =
        "\n" + cells.toString() // Переопределение метода toString для удобства отображения данных ячеек
}
