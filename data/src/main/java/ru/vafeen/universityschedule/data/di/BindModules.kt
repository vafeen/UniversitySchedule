package ru.vafeen.universityschedule.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.universityschedule.data.database.DatabaseRepositoryImpl
import ru.vafeen.universityschedule.data.network.ApkDownloaderImpl
import ru.vafeen.universityschedule.data.network.ApkInstallerImpl
import ru.vafeen.universityschedule.data.network.NetworkRepositoryImpl
import ru.vafeen.universityschedule.data.notifications.NotificationBuilderImpl
import ru.vafeen.universityschedule.data.notifications.NotificationServiceImpl
import ru.vafeen.universityschedule.data.scheduler.SchedulerImpl
import ru.vafeen.universityschedule.domain.database.AppDatabase
import ru.vafeen.universityschedule.domain.database.DatabaseRepository
import ru.vafeen.universityschedule.domain.network.repository.NetworkRepository
import ru.vafeen.universityschedule.domain.network.service.ApkDownloader
import ru.vafeen.universityschedule.domain.network.service.ApkInstaller
import ru.vafeen.universityschedule.domain.notifications.NotificationBuilder
import ru.vafeen.universityschedule.domain.notifications.NotificationService
import ru.vafeen.universityschedule.domain.scheduler.Scheduler
import ru.vafeen.universityschedule.domain.utils.SharedPreferencesValue


val networkModule = module {
    single<NetworkRepository> {
        val networkRepositoryImpl: NetworkRepositoryImpl by inject(clazz = NetworkRepositoryImpl::class.java)
        networkRepositoryImpl
    }
}

val databaseModule = module {
    single<DatabaseRepository> {
        val databaseRepositoryImpl: DatabaseRepositoryImpl by inject(clazz = DatabaseRepositoryImpl::class.java)
        databaseRepositoryImpl
    }
    single<AppDatabase> {
        Room.databaseBuilder(
            context = get(), klass = AppDatabase::class.java, name = "universityScheduleDB.db"
        ).addMigrations(
            AppDatabase.MIGRATION_1_2,
            AppDatabase.MIGRATION_2_3,
            AppDatabase.MIGRATION_3_4,
            AppDatabase.MIGRATION_4_5,
        ).build()
    }
}
val servicesModule = module {
    single<NotificationService> {
        val notificationServiceImpl: NotificationServiceImpl by inject(clazz = NotificationServiceImpl::class.java)
        notificationServiceImpl
    }
    single<NotificationBuilder> {
        val notificationBuilderImpl: NotificationBuilderImpl by inject(clazz = NotificationBuilderImpl::class.java)
        notificationBuilderImpl
    }
    single<Scheduler> {
        val schedulerImpl: SchedulerImpl by inject(clazz = SchedulerImpl::class.java)
        schedulerImpl
    }
    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            SharedPreferencesValue.Name.key, Context.MODE_PRIVATE
        )
    }
    single<ApkDownloader> {
        val apkDownloaderImpl: ApkDownloaderImpl by inject(clazz = ApkDownloaderImpl::class.java)
        apkDownloaderImpl
    }
    single<ApkInstaller> {
        val apkInstallerImpl: ApkInstallerImpl by inject(clazz = ApkInstallerImpl::class.java)
        apkInstallerImpl
    }
}


