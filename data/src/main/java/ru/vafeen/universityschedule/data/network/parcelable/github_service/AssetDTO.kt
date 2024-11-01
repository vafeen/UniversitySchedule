package ru.vafeen.universityschedule.data.network.parcelable.github_service

import com.google.gson.annotations.SerializedName

data class AssetDTO(
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