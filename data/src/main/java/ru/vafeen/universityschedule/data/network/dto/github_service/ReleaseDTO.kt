package ru.vafeen.universityschedule.data.network.dto.github_service

import com.google.gson.annotations.SerializedName

/**
 * Данные о релизе из GitHub API.
 *
 * @property url URL релиза.
 * @property assetsUrl URL для получения активов (assets) релиза.
 * @property uploadUrl URL для загрузки активов в релиз.
 * @property htmlUrl URL релиза в формате HTML.
 * @property id Уникальный идентификатор релиза.
 * @property author Информация об авторе релиза.
 * @property nodeId Идентификатор узла релиза.
 * @property tagName Название тега, связанного с релизом.
 * @property targetCommitish Указывает на коммит, к которому относится релиз.
 * @property name Название релиза.
 * @property draft Флаг, указывающий, является ли релиз черновиком.
 * @property preRelease Флаг, указывающий, является ли релиз предварительным.
 * @property createdAt Дата и время создания релиза.
 * @property publishedAt Дата и время публикации релиза.
 * @property assets Список активов, связанных с релизом.
 * @property tarballUrl URL для загрузки tarball архива релиза.
 * @property zipballUrl URL для загрузки zip архива релиза.
 * @property body Описание релиза.
 */
internal data class ReleaseDTO(
    @SerializedName("url") val url: String,
    @SerializedName("assets_url") val assetsUrl: String,
    @SerializedName("upload_url") val uploadUrl: String,
    @SerializedName("html_url") val htmlUrl: String,
    @SerializedName("id") val id: Long,
    @SerializedName("author") val author: AuthorDTO,
    @SerializedName("node_id") val nodeId: String,
    @SerializedName("tag_name") val tagName: String,
    @SerializedName("target_commitish") val targetCommitish: String,
    @SerializedName("name") val name: String,
    @SerializedName("draft") val draft: Boolean,
    @SerializedName("prerelease") val preRelease: Boolean,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("published_at") val publishedAt: String,
    @SerializedName("assets") val assets: List<AssetDTO>,
    @SerializedName("tarball_url") val tarballUrl: String,
    @SerializedName("zipball_url") val zipballUrl: String,
    @SerializedName("body") val body: String
)
