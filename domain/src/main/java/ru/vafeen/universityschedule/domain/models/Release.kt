package ru.vafeen.universityschedule.domain.models

/**
 * Класс, представляющий релиз.
 *
 * @property tagName Название тега релиза.
 * @property assets Список активов (файлов), связанных с релизом.
 * @property body Описание или примечания к релизу.
 */
data class Release(
    val tagName: String,
    val assets: List<String>,
    val body: String
)
