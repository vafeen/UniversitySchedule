package ru.vafeen.universityschedule.domain.network.result_status.latest_release

class SuccessReleaseResult<T>(
    val data: T
) : ReleaseResult<T>()