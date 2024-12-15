package ru.vafeen.universityschedule.data.impl.network.end_points

internal object GitHubDataServiceLink {
    const val BASE_LINK = "https://api.github.com/"

    object EndPoint {
        const val LATEST_RELEASE_INFO = "repos/vafeen/UniversitySchedule/releases/latest"
    }
}