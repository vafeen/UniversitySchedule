package ru.vafeen.universityschedule.domain.models

import java.io.InputStream

data class CompositeFileStream(
    val stream: InputStream,
    val contentLength: Long
)