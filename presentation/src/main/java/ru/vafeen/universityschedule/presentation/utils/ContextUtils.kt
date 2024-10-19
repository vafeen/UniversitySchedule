package ru.vafeen.universityschedule.presentation.utils

import android.content.Context

fun Context.pathToDownloadRelease(): String = "${externalCacheDir?.absolutePath}/app-release.apk"
