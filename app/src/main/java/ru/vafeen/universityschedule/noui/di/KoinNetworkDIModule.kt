package ru.vafeen.universityschedule.noui.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vafeen.universityschedule.network.end_points.DownloadServiceLink
import ru.vafeen.universityschedule.network.end_points.GHDServiceLink
import ru.vafeen.universityschedule.network.repository.NetworkRepository
import ru.vafeen.universityschedule.network.service.DownloadService
import ru.vafeen.universityschedule.network.service.GitHubDataService

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
    singleOf(::NetworkRepository)
}