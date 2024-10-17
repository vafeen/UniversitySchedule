package ru.vafeen.universityschedule.domain.utils

import android.content.ClipboardManager
import android.content.Context

fun Context.pasteText(): String? {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    return clipboard.primaryClip?.getItemAt(0)?.text?.toString()
}