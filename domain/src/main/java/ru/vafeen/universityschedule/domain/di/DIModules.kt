package ru.vafeen.universityschedule.domain.di

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.vafeen.universityschedule.data.database.DatabaseRepository
import ru.vafeen.universityschedule.data.network.repository.NetworkRepository
import ru.vafeen.universityschedule.domain.converters.LessonConverter
import ru.vafeen.universityschedule.domain.converters.ReleaseConverter
import ru.vafeen.universityschedule.domain.converters.ReminderConverter
import ru.vafeen.universityschedule.domain.database.DatabaseRepositoryImpl
import ru.vafeen.universityschedule.domain.network.NetworkRepositoryImpl
import ru.vafeen.universityschedule.domain.network.downloader.Downloader
import ru.vafeen.universityschedule.domain.notifications.NotificationService
import ru.vafeen.universityschedule.domain.planner.Scheduler
import ru.vafeen.universityschedule.domain.planner.usecase.CancelJobUseCase
import ru.vafeen.universityschedule.domain.planner.usecase.ScheduleRepeatingJobUseCase
import ru.vafeen.universityschedule.domain.shared_preferences.SharedPreferencesValue
import ru.vafeen.universityschedule.domain.usecase.db.CleverUpdatingLessonsUseCase
import ru.vafeen.universityschedule.domain.usecase.db.DeleteLessonsUseCase
import ru.vafeen.universityschedule.domain.usecase.db.DeleteRemindersUseCase
import ru.vafeen.universityschedule.domain.usecase.db.GetAsFlowLessonsUseCase
import ru.vafeen.universityschedule.domain.usecase.db.GetAsFlowRemindersUseCase
import ru.vafeen.universityschedule.domain.usecase.db.GetReminderByIdOfReminderUseCase
import ru.vafeen.universityschedule.domain.usecase.db.InsertLessonsUseCase
import ru.vafeen.universityschedule.domain.usecase.db.InsertRemindersUseCase
import ru.vafeen.universityschedule.domain.usecase.db.UpdateLessonsUseCase
import ru.vafeen.universityschedule.domain.usecase.db.UpdateRemindersUseCase
import ru.vafeen.universityschedule.domain.usecase.network.DownloadFileUseCase
import ru.vafeen.universityschedule.domain.usecase.network.GetLatestReleaseUseCase
import ru.vafeen.universityschedule.domain.usecase.network.GetSheetDataAndUpdateDBUseCase
import ru.vafeen.universityschedule.domain.usecase.network.GetSheetDataUseCase

val convertersDomainModule = module {
    singleOf(::LessonConverter)
    singleOf(::ReleaseConverter)
    singleOf(::ReminderConverter)
}

val plannerDomainModule = module {
    singleOf(::ScheduleRepeatingJobUseCase)
    singleOf(::CancelJobUseCase)
}

val networkDomainModule = module {
    singleOf(::DownloadFileUseCase)
    singleOf(::GetLatestReleaseUseCase)
    singleOf(::GetSheetDataUseCase)
    single<NetworkRepository> { NetworkRepositoryImpl(get(), get(), get()) }
}

val databaseDomainModule = module {
    singleOf(::DeleteLessonsUseCase)
    singleOf(::DeleteRemindersUseCase)
    singleOf(::GetAsFlowLessonsUseCase)
    singleOf(::GetAsFlowRemindersUseCase)
    singleOf(::GetReminderByIdOfReminderUseCase)
    singleOf(::InsertLessonsUseCase)
    singleOf(::InsertRemindersUseCase)
    singleOf(::UpdateLessonsUseCase)
    singleOf(::UpdateRemindersUseCase)
    singleOf(::CleverUpdatingLessonsUseCase)
    singleOf(::GetSheetDataAndUpdateDBUseCase)
    single<DatabaseRepository> { DatabaseRepositoryImpl(get()) }
}

val koinServicesDIModule = module {
    singleOf(::NotificationService)
    singleOf(::Scheduler)
    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            SharedPreferencesValue.Name.key, Context.MODE_PRIVATE
        )
    }
    singleOf(::Downloader)
}

