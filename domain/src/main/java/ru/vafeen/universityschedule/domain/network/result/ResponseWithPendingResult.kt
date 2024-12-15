package ru.vafeen.universityschedule.domain.network.result

sealed class ResponseWithPendingResult<T> {
    class Error<T>(val exception: Exception) : ResponseWithPendingResult<T>()
    class Pending<T> : ResponseWithPendingResult<T>()
    class Success<T>(val data: T) : ResponseWithPendingResult<T>()
}