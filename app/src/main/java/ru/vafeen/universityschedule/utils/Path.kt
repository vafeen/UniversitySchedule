package ru.vafeen.universityschedule.utils

import android.content.Context

object Path {
    val path = { context: Context -> "${context.externalCacheDir?.absolutePath}/app-release.apk" }
}