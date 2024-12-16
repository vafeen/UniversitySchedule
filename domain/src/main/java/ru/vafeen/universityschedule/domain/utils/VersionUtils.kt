package ru.vafeen.universityschedule.domain.utils

import android.content.Context
import android.os.Build

/**
 * Получает имя версии приложения.
 *
 * Эта функция возвращает имя версии текущего приложения,
 * используя [PackageManager].
 *
 * @return Имя версии приложения в виде строки или null, если информация не доступна.
 */
fun Context.getVersionName(): String? =
    packageManager.getPackageInfo(packageName, 0).versionName

/**
 * Получает код версии приложения.
 *
 * Эта функция возвращает код версии текущего приложения.
 * В зависимости от версии Android используется либо longVersionCode,
 * либо обычный versionCode.
 *
 * @return Код версии приложения в виде [Long].
 */
fun Context.getVersionCode(): Long =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
        packageManager.getPackageInfo(packageName, 0).longVersionCode
    else
        packageManager.getPackageInfo(packageName, 0).versionCode.toLong()
