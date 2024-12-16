package ru.vafeen.universityschedule.data.network.dto.googlesheets_service

import com.google.gson.annotations.SerializedName

/**
 * Класс, представляющий таблицу из данных Google Sheets.
 *
 * @property cols Список столбцов таблицы.
 * @property rows Список строк таблицы.
 * @property parsedNumHeaders Количество заголовков, распознанных в таблице.
 */
internal data class TableDTO(
    @SerializedName("cols") val cols: List<ColumnDTO>, // Список столбцов таблицы
    @SerializedName("rows") val rows: List<RowDTO>, // Список строк таблицы
    @SerializedName("parsedNumHeaders") val parsedNumHeaders: Int // Количество заголовков
) {
    override fun toString(): String =
        rows.toString() // Переопределение метода toString для удобства отображения данных строк
}
