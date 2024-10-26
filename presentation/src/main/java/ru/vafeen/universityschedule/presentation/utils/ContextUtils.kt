package ru.vafeen.universityschedule.presentation.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import  ru.vafeen.universityschedule.data.R

internal fun Context.sendEmail(email: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data =
            Uri.parse("mailto:$email?subject=${Uri.encode("BugReport ${getString(R.string.app_name)}")}")
    }
    startActivity(intent)
}