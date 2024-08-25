package ru.vafeen.universityschedule.network.parcelable.github_service

data class Asset(
    val url: String,
    val id: Long,
    val node_id: String,
    val name: String,
    val label: String?,
    val uploader: Author,
    val content_type: String,
    val state: String,
    val size: Long,
    val download_count: Int,
    val created_at: String,
    val updated_at: String,
    val browser_download_url: String
)