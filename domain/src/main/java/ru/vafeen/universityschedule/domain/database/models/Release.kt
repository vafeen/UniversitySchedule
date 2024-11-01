package ru.vafeen.universityschedule.domain.database.models

data class Release(
    val tagName: String,
    val assets: List<String>
)