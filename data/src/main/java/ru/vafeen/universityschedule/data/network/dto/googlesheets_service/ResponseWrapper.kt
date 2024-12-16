package ru.vafeen.universityschedule.data.network.dto.googlesheets_service

import com.google.gson.annotations.SerializedName

/**
 * Класс, в который парсятся данные из Google Sheets.
 *
 * @property version Версия ответа от API.
 * @property reqId Идентификатор запроса.
 * @property status Статус ответа (например, "ok").
 * @property sig Подпись ответа для проверки целостности данных.
 * @property table Объект таблицы, содержащий данные из Google Sheets.
 */
internal data class ResponseWrapper(
    @SerializedName("version") val version: String,
    @SerializedName("reqId") val reqId: String,
    @SerializedName("status") val status: String,
    @SerializedName("sig") val sig: String,
    @SerializedName("table") val table: TableDTO
) {
    override fun toString(): String =
        table.toString() // Переопределение метода toString для удобства отображения данных таблицы
}
