package ru.vafeen.universityschedule.domain

internal object GSheetsServiceLink {
    const val BASE_URL = "https://docs.google.com/spreadsheets/d/"
    object EndPoint {
        const val JSON = "gviz/tq?tqx=out:json"
    }
}
