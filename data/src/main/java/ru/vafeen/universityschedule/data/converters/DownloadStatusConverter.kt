package ru.vafeen.universityschedule.data.converters

import ru.vafeen.universityschedule.domain.converter.BaseConverter
import ru.vafeen.universityschedule.domain.network.result.DownloadStatus
import android.vafeen.direct_refresher.downloader.DownloadStatus as LibDownloadStatus

internal class DownloadStatusConverter : BaseConverter<LibDownloadStatus, DownloadStatus> {
    override fun convertAB(a: LibDownloadStatus): DownloadStatus = when (a) {
        is LibDownloadStatus.Error -> DownloadStatus.Error(a.exception)
        is LibDownloadStatus.InProgress -> DownloadStatus.InProgress(a.percentage)
        LibDownloadStatus.Started -> DownloadStatus.Started
        LibDownloadStatus.Success -> DownloadStatus.Success
    }

    override fun convertBA(b: DownloadStatus): LibDownloadStatus =
        throw Exception("ru.vafeen.universityschedule.data.converters.DownloadStatusConverter is not defined")

}