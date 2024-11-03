package ru.vafeen.universityschedule.presentation.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import ru.vafeen.universityschedule.resources.R

internal fun Context.sendEmail(email: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data =
            Uri.parse("mailto:$email?subject=${Uri.encode("BugReport ${getString(R.string.app_name)}")}")
    }
    startActivity(intent)
}

internal fun Context.copyTextToClipBoard(label: String, text: String) {
    val clipboard =
        getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, text)
    if (text.isNotEmpty()) clipboard.setPrimaryClip(clip)
}

internal fun Context.openLink(link: String) {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
}

fun Context.pasteText(): String? {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    return clipboard.primaryClip?.getItemAt(0)?.text?.toString()
}