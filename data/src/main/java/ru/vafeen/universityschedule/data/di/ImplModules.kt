package ru.vafeen.universityschedule.data.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.vafeen.universityschedule.data.database.DatabaseRepositoryImpl
import ru.vafeen.universityschedule.data.network.ApkDownloaderImpl
import ru.vafeen.universityschedule.data.network.ApkInstallerImpl
import ru.vafeen.universityschedule.data.network.NetworkRepositoryImpl
import ru.vafeen.universityschedule.data.notifications.NotificationBuilderImpl
import ru.vafeen.universityschedule.data.notifications.NotificationServiceImpl
import ru.vafeen.universityschedule.data.scheduler.SchedulerImpl


val networkModuleImpl = module {
    singleOf(::NetworkRepositoryImpl)
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