package ru.vafeen.universityschedule.domain.models

/**
 * Класс, представляющий информацию о релизе приложения.
 *
 * @property tagName Название тега релиза (версия приложения).
 * @property apkUrl URL для загрузки APK-файла.
 * @property size Размер APK-файла в байтах.
 * @property body Описание изменений или примечаний к релизу.
 */
data class Release(
    val tagName: String,
    val apkUrl: String,
    val size: Long,
    val body: String
)
