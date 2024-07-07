package ru.vafeen.universityschedule.utils

import android.content.Context

fun getVersionName(context: Context): String {
    val packageManager = context.packageManager
    val packageName = context.packageName
    val packageInfo = packageManager.getPackageInfo(packageName, 0)
    return packageInfo.versionName
}

