package ru.vafeen.universityschedule.data.network.end_points

internal object GHDServiceLink {
    const val BASE_LINK = "https://api.github.com/"

    object EndPoint {
        const val LATEST_RELEASE_INFO = "repos/vafeen/UniversitySchedule/releases/latest"
    }
}