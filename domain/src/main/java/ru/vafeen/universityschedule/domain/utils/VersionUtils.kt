package ru.vafeen.universityschedule.domain.utils

import android.content.Context

fun Context.getVersionName(): String? =
    packageManager.getPackageInfo(packageName, 0).versionName


