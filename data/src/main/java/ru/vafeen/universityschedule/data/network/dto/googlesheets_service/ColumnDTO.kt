package ru.vafeen.universityschedule.data.network.dto.googlesheets_service

import com.google.gson.annotations.SerializedName

/**
 * Класс, представляющий столбец таблицы из данных Google Sheets.
 *
 * @property id Уникальный идентификатор столбца.
 * @property label Метка, отображаемая для столбца.
 * @property type Тип данных в столбце (например, "string", "number" и т.д.), может быть null.
 */
internal data class ColumnDTO(
    @SerializedName("id") val id: String, // Уникальный идентификатор столбца
    @SerializedName("label") val label: String, // Метка столбца
    @SerializedName("type") val type: String? // Тип данных в столбце
) {
    override fun toString(): String {
        return "|id=$id, label=$label, type=$type|" // Переопределение метода toString для удобства отображения данных столбца
    }
}
