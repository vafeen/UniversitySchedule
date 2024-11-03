package ru.vafeen.universityschedule.domain.di

import androidx.room.Room
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vafeen.universityschedule.domain.converters.DateTimeConverter
import ru.vafeen.universityschedule.domain.converters.LessonConverter
import ru.vafeen.universityschedule.domain.converters.ReleaseConverter
import ru.vafeen.universityschedule.domain.converters.ReminderConverter
import ru.vafeen.universityschedule.domain.converters.TimeConverter
import ru.vafeen.universityschedule.domain.database.AppDatabase
import ru.vafeen.universityschedule.domain.network.end_points.DownloadServiceLink
import ru.vafeen.universityschedule.domain.network.end_points.GitHubDataServiceLink
import ru.vafeen.universityschedule.domain.network.end_points.GoogleSheetsServiceLink
import ru.vafeen.universityschedule.domain.network.service.DownloadService
import ru.vafeen.universityschedule.domain.network.service.GitHubDataService
import ru.vafeen.universityschedule.domain.network.service.GoogleSheetsService

val converterModule = module {
    singleOf(::DateTimeConverter)
    singleOf(::LessonConverter)
    singleOf(::ReleaseConverter)
    singleOf(::ReminderConverter)
    singleOf(::TimeConverter)
}
val roomDatabaseModule = module {

}
val networkServices = module {
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

