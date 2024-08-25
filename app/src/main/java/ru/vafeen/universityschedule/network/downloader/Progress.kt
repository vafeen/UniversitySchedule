package ru.vafeen.universityschedule.network.downloader

data class Progress(
    val totalBytesRead: Long,
    val contentLength: Long,
    val done: Boolean
)
