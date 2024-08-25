package ru.vafeen.universityschedule.noui.dependency_injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vafeen.universityschedule.network.end_points.DownloadServiceLink
import ru.vafeen.universityschedule.network.end_points.GHDServiceLink
import ru.vafeen.universityschedule.network.service.DownloadService
import ru.vafeen.universityschedule.network.service.GitHubDataService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitDIModule {

    @Provides
    @Singleton
    fun provideGHDService(): GitHubDataService = Retrofit.Builder()
        .baseUrl(GHDServiceLink.BASE_LINK)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(GitHubDataService::class.java)

    @Provides
    @Singleton
    fun provideDownloadService(): DownloadService = Retrofit.Builder()
        .baseUrl(DownloadServiceLink.BASE_LINK)
        .build().create(DownloadService::class.java)

}