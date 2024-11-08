package ru.vafeen.universityschedule.domain.utils

import android.content.Context
import android.os.Build

fun Context.getVersionName(): String? =
    packageManager.getPackageInfo(packageName, 0).versionName

fun Context.getVersionCode(): Long =  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
        packageManager.getPackageInfo(packageName, 0).longVersionCode
    else packageManager.getPackageInfo(packageName, 0).versionCode.toLong()


