package ru.vafeen.universityschedule.domain.network.service

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import ru.vafeen.universityschedule.domain.network.result.DownloadStatus

/**
 * Interface for refreshing Android applications by downloading and installing APK files.
 */
interface Refresher {

    /**
     * A flow that broadcasts the progress of the download process.
     * It emits updates on the status of the download, such as started, in-progress, success, or error.
     */
    val progressFlow: Flow<DownloadStatus>

    /**
     * Starts the download of an APK file from the provided URL and installs it once downloaded.
     * This function downloads the file and tracks the progress of the download.
     *
     * @param coroutineScope The coroutine scope in which the download will be executed.
     * @param url The URL of the APK file to be downloaded.
     * @param downloadedFileName The name under which the downloaded APK file will be saved.
     */
    suspend fun refresh(coroutineScope: CoroutineScope, url: String, downloadedFileName: String)
}
