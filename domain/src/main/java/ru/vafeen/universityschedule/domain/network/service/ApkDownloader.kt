package ru.vafeen.universityschedule.domain.network.service

import android.content.Context
import kotlinx.coroutines.flow.SharedFlow

interface ApkDownloader {
    val percentageFlow: SharedFlow<Float>
    val isUpdateInProcessFlow: SharedFlow<Boolean>
    fun downloadApk(
        context: Context,
        url: String
    )
}