package ru.vafeen.universityschedule.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun openLink(context: Context, link: String) {
    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
}