package ru.vafeen.universityschedule.data.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vafeen.universityschedule.data.network.downloader.Downloader
import ru.vafeen.universityschedule.data.network.end_points.DownloadServiceLink
import ru.vafeen.universityschedule.data.network.end_points.GHDServiceLink
import ru.vafeen.universityschedule.data.network.service.DownloadService
import ru.vafeen.universityschedule.data.network.service.GitHubDataService

val koinNetworkDIModule = module {
    single<GitHubDataService> {
        Retrofit.Builder()
            .baseUrl(GHDServiceLink.BASE_LINK)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(GitHubDataService::class.java)
    }
    single<DownloadService> {
        Retrofit.Builder()
            .baseUrl(DownloadServiceLink.BASE_LINK)
            .build().create(DownloadService::class.java)
    }
    singleOf(::Downloader)
}