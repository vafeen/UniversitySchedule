package ru.vafeen.universityschedule.domain.models

data class Release(
    val tagName: String,
    val assets: List<String>
)