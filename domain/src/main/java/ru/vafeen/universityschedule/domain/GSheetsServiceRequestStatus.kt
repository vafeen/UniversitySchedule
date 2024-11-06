package ru.vafeen.universityschedule.domain

enum class GSheetsServiceRequestStatus {
    Success,
    NetworkError,
    Waiting,
    NoLink
}