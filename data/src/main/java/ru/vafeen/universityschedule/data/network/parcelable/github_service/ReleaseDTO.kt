package ru.vafeen.universityschedule.data.network.parcelable.github_service

import com.google.gson.annotations.SerializedName

data class ReleaseDTO(
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

