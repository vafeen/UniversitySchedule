package ru.vafeen.universityschedule.noui.dependency_injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vafeen.universityschedule.network.downloader.DownloadService
import ru.vafeen.universityschedule.network.service.GitHubDataService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitDIModule {

    @Provides
    @Singleton
    fun provideGHDService(): GitHubDataService = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(GitHubDataService::class.java)

    @Provides
    @Singleton
    fun provideDownloadService(): DownloadService = Retrofit.Builder()
        .baseUrl("https://github.com/") // Base URL is required but will be overridden
        .build().create(DownloadService::class.java)

}