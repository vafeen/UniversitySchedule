package ru.vafeen.universityschedule.data.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

fun Context.copyTextToClipBoard(text: String) {
    val clipboard =
        getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label", text)
    if (text.isNotEmpty()) clipboard.setPrimaryClip(clip)
}