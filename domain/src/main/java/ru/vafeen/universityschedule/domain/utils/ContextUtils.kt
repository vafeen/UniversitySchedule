package ru.vafeen.universityschedule.domain.utils

import android.content.Context

internal fun Context.pathToDownloadRelease(): String =
    "${externalCacheDir?.absolutePath}/app-release.apk"