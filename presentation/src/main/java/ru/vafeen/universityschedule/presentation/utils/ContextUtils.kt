package ru.vafeen.universityschedule.presentation.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

fun Context.pathToDownloadRelease(): String = "${externalCacheDir?.absolutePath}/app-release.apk"

fun Context.copyStackTraceToClipboardWithAddingContactUs(thread: Thread, throwable: Throwable) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(
        "Error",
        "Contact us about this problem: ${Link.MAIL}\n Exception in ${thread.name} thread\n${throwable.stackTraceToString()}"
    )
    clipboard.setPrimaryClip(clip)
}