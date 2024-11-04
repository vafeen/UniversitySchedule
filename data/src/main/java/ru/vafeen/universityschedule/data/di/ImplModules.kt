package ru.vafeen.universityschedule.data.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vafeen.universityschedule.data.impl.database.DatabaseRepositoryImpl
import ru.vafeen.universityschedule.data.impl.network.repository.DownloadFileRepositoryImpl
import ru.vafeen.universityschedule.data.impl.network.repository.GoogleSheetsRepositoryImpl
import ru.vafeen.universityschedule.data.impl.network.repository.ReleaseRepositoryImpl
import ru.vafeen.universityschedule.data.impl.network.service.ApkDownloaderImpl
import ru.vafeen.universityschedule.data.impl.network.service.ApkInstallerImpl
import ru.vafeen.universityschedule.data.impl.notifications.NotificationBuilderImpl
import ru.vafeen.universityschedule.data.impl.notifications.NotificationServiceImpl
import ru.vafeen.universityschedule.data.impl.scheduler.SchedulerImpl
import ru.vafeen.universityschedule.domain.network.end_points.DownloadServiceLink
import ru.vafeen.universityschedule.domain.network.end_points.GitHubDataServiceLink
import ru.vafeen.universityschedule.domain.network.end_points.GoogleSheetsServiceLink
import ru.vafeen.universityschedule.domain.network.service.DownloadService
import ru.vafeen.universityschedule.domain.network.service.GitHubDataService
import ru.vafeen.universityschedule.domain.network.service.GoogleSheetsService


val networkRepositoryModuleImpl = module {
    singleOf(::DownloadFileRepositoryImpl)
    singleOf(::GoogleSheetsRepositoryImpl)
    singleOf(::ReleaseRepositoryImpl)
}
val networkServiceModuleImpl = module {
    single<GitHubDataService> {
        Retrofit.Builder()
            .baseUrl(GitHubDataServiceLink.BASE_LINK)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(GitHubDataService::class.java)
    }
    single<DownloadService> {
        Retrofit.Builder()
            .baseUrl(DownloadServiceLink.BASE_LINK)
            .build().create(DownloadService::class.java)
    }
    single<GoogleSheetsService> {
        Retrofit.Builder()
            .baseUrl(GoogleSheetsServiceLink.BASE_URL)
            .build().create(GoogleSheetsService::class.java)
    }
}
val databaseModuleImpl = module {
    singleOf(::DatabaseRepositoryImpl)
}

val servicesModuleImpl = module {
    singleOf(::NotificationServiceImpl)
    singleOf(::NotificationBuilderImpl)
    singleOf(::SchedulerImpl)
    singleOf(::ApkDownloaderImpl)
    singleOf(::ApkInstallerImpl)
}