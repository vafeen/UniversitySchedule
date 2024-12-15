package ru.vafeen.universityschedule.domain.network.service

import kotlinx.coroutines.flow.SharedFlow

interface Downloader {
    val percentageFlow: SharedFlow<Float>
    val isUpdateInProcessFlow: SharedFlow<Boolean>
    fun downloadApk(url: String)
}