package ru.vafeen.universityschedule.domain.network.result_status.latest_release

class ErrorReleaseResult<T>(
    val exception: Exception
) : ReleaseResult<T>()
