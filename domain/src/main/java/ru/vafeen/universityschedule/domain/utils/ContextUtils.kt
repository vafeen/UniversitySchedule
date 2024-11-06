package ru.vafeen.universityschedule.domain.utils

import android.content.Context

fun Context.pathToDownloadRelease(): String =
    "${externalCacheDir?.absolutePath}/app-release.apk"