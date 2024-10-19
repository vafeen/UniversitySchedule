package ru.vafeen.universityschedule.presentation.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

internal fun Context.pathToDownloadRelease(): String =
    "${externalCacheDir?.absolutePath}/app-release.apk"

internal fun Context.sendEmail(email: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data =
            Uri.parse("mailto:$email?subject=${Uri.encode("BugReport")}")
    }
    startActivity(intent)
}