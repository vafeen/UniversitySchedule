package ru.vafeen.universityschedule.domain.models

import java.io.InputStream

/**
 * Класс, представляющий составной поток файла.
 *
 * @property stream Входной поток данных файла.
 * @property contentLength Длина содержимого файла в байтах.
 */
data class CompositeFileStream(
    val stream: InputStream, // Входной поток данных файла
    val contentLength: Long // Длина содержимого файла в байтах
)
