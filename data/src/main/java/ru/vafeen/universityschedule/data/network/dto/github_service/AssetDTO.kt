package ru.vafeen.universityschedule.data.network.dto.github_service

import com.google.gson.annotations.SerializedName

/**
 * Данные об активе (asset) из GitHub API.
 *
 * @property url URL актива.
 * @property id Уникальный идентификатор актива.
 * @property nodeId Идентификатор узла актива.
 * @property name Название актива.
 * @property label Метка актива (может быть null).
 * @property uploader Информация о загрузчике актива.
 * @property contentType MIME-тип содержимого актива.
 * @property state Состояние актива (например, "available" или "pending").
 * @property size Размер актива в байтах.
 * @property downloadCount Количество загрузок актива.
 * @property createdAt Дата и время создания актива.
 * @property updatedAt Дата и время последнего обновления актива.
 * @property browserDownloadUrl URL для загрузки актива через браузер.
 */
internal data class AssetDTO(
    @SerializedName("url") val url: String,
    @SerializedName("id") val id: Long,
    @SerializedName("node_id") val nodeId: String,
    @SerializedName("name") val name: String,
    @SerializedName("label") val label: String?,
    @SerializedName("uploader") val uploader: AuthorDTO,
    @SerializedName("content_type") val contentType: String,
    @SerializedName("state") val state: String,
    @SerializedName("size") val size: Long,
    @SerializedName("download_count") val downloadCount: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("browser_download_url") val browserDownloadUrl: String
)
