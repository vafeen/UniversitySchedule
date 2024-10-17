package ru.vafeen.universityschedule.domain.di

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.vafeen.universityschedule.data.database.DTConverters
import ru.vafeen.universityschedule.data.database.DatabaseRepository
import ru.vafeen.universityschedule.data.network.repository.NetworkRepository
import ru.vafeen.universityschedule.domain.database.DatabaseRepositoryImpl
import ru.vafeen.universityschedule.domain.database.usecase.DeleteAllLessonsUseCase
import ru.vafeen.universityschedule.domain.database.usecase.DeleteAllRemindersUseCase
import ru.vafeen.universityschedule.domain.database.usecase.GetAllAsFlowLessonsUseCase
import ru.vafeen.universityschedule.domain.database.usecase.GetAllAsFlowRemindersUseCase
import ru.vafeen.universityschedule.domain.database.usecase.GetReminderByIdOfReminderUseCase
import ru.vafeen.universityschedule.domain.database.usecase.InsertAllLessonsUseCase
import ru.vafeen.universityschedule.domain.database.usecase.InsertAllRemindersUseCase
import ru.vafeen.universityschedule.domain.database.usecase.UpdateAllLessonsUseCase
import ru.vafeen.universityschedule.domain.database.usecase.UpdateAllRemindersUseCase
import ru.vafeen.universityschedule.domain.network.NetworkRepositoryImpl
import ru.vafeen.universityschedule.domain.network.usecase.DownloadFileUseCase
import ru.vafeen.universityschedule.domain.network.usecase.GetLatestReleaseUseCase
import ru.vafeen.universityschedule.domain.notifications.NotificationService
import ru.vafeen.universityschedule.domain.planner.Scheduler
import ru.vafeen.universityschedule.domain.shared_preferences.SharedPreferencesValue

val networkDomainModule = module {
    singleOf(::DownloadFileUseCase)
    singleOf(::GetLatestReleaseUseCase)
    single<NetworkRepository> { NetworkRepositoryImpl(get(), get()) }
}

val databaseDomainModule = module {
    singleOf(::DeleteAllLessonsUseCase)
    singleOf(::DeleteAllRemindersUseCase)
    singleOf(::GetAllAsFlowLessonsUseCase)
    singleOf(::GetAllAsFlowRemindersUseCase)
    singleOf(::GetReminderByIdOfReminderUseCase)
    singleOf(::InsertAllLessonsUseCase)
    singleOf(::InsertAllRemindersUseCase)
    singleOf(::UpdateAllLessonsUseCase)
    singleOf(::UpdateAllRemindersUseCase)
    single<ru.vafeen.universityschedule.data.database.DatabaseRepository> {
        DatabaseRepositoryImpl(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
        )
    }
}

val koinServicesDIModule = module {
    singleOf(::NotificationService)
    singleOf(::Scheduler)
    singleOf(::DTConverters)
    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            SharedPreferencesValue.Name.key, Context.MODE_PRIVATE
        )
    }
}

