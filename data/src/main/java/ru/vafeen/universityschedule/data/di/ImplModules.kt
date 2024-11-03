package ru.vafeen.universityschedule.data.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.vafeen.universityschedule.data.impl.database.DatabaseRepositoryImpl
import ru.vafeen.universityschedule.data.impl.network.ApkDownloaderImpl
import ru.vafeen.universityschedule.data.impl.network.ApkInstallerImpl
import ru.vafeen.universityschedule.data.impl.network.NetworkRepositoryImpl
import ru.vafeen.universityschedule.data.impl.notifications.NotificationBuilderImpl
import ru.vafeen.universityschedule.data.impl.notifications.NotificationServiceImpl
import ru.vafeen.universityschedule.data.impl.scheduler.SchedulerImpl


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