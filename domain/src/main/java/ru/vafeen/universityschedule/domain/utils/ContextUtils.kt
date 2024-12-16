package ru.vafeen.universityschedule.domain.utils

import android.content.Context

/**
 * Расширение для класса [Context], позволяющее получить путь к файлу для загрузки релиза.
 *
 * Эта функция формирует полный путь к файлу APK, который будет загружен в кэш приложения.
 *
 * @return Путь к файлу APK для загрузки релиза. Возвращает строку, представляющую путь к файлу.
 */
fun Context.pathToDownloadRelease(): String =
    "${externalCacheDir?.absolutePath}/app-release.apk"
