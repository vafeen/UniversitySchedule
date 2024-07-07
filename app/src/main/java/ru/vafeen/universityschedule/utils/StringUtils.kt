package ru.vafeen.universityschedule.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

fun String.removeSubStrings(vararg substrings: String): String {
    var result = this
    substrings.forEach {
        result = result.replace(it, "")
    }
    return result
}

fun String.dataToJsonString(): String = substringAfter("Query.setResponse(").let {
    it.substring(0, it.lastIndex - 1).removeSubStrings("null,")
}

fun Context.copyTextToClipBoard(text: String) {
    val clipboard =
        getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label", text)
    if (text.isNotEmpty()) clipboard.setPrimaryClip(clip)
}

fun Context.pasteText(): String? {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    return clipboard.primaryClip?.getItemAt(0)?.text?.toString()
}