package ru.vafeen.universityschedule.domain.network.result

/**
 * Sealed class for representing the status of a download operation.
 */
sealed class DownloadStatus {

    /**
     * Represents the start of the download process.
     */
    data object Started : DownloadStatus()

    /**
     * Represents the progress of the download with the current percentage.
     *
     * @property percentage The percentage of the file downloaded, expressed as a float between 0.0 and 1.0.
     */
    class InProgress(val percentage: Float) : DownloadStatus()

    /**
     * Indicates the successful completion of the download process.
     */
    data object Success : DownloadStatus()

    /**
     * Represents an error that occurred during the download process.
     *
     * @property exception The exception describing the error.
     */
    class Error(val exception: Exception) : DownloadStatus()
}
