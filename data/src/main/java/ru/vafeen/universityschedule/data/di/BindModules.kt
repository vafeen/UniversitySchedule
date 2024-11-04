package ru.vafeen.universityschedule.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.universityschedule.data.database.AppDatabase
import ru.vafeen.universityschedule.data.impl.database.DatabaseRepositoryImpl
import ru.vafeen.universityschedule.data.impl.network.repository.DownloadFileRepositoryImpl
import ru.vafeen.universityschedule.data.impl.network.repository.GoogleSheetsRepositoryImpl
import ru.vafeen.universityschedule.data.impl.network.repository.ReleaseRepositoryImpl
import ru.vafeen.universityschedule.data.impl.network.service.ApkDownloaderImpl
import ru.vafeen.universityschedule.data.impl.network.service.ApkInstallerImpl
import ru.vafeen.universityschedule.data.impl.notifications.NotificationBuilderImpl
import ru.vafeen.universityschedule.data.impl.notifications.NotificationServiceImpl
import ru.vafeen.universityschedule.data.impl.scheduler.SchedulerImpl
import ru.vafeen.universityschedule.domain.database.DatabaseRepository
import ru.vafeen.universityschedule.domain.network.repository.DownloadFileRepository
import ru.vafeen.universityschedule.domain.network.repository.GoogleSheetsRepository
import ru.vafeen.universityschedule.domain.network.repository.ReleaseRepository
import ru.vafeen.universityschedule.domain.network.service.ApkDownloader
import ru.vafeen.universityschedule.domain.network.service.ApkInstaller
import ru.vafeen.universityschedule.domain.notifications.NotificationBuilder
import ru.vafeen.universityschedule.domain.notifications.NotificationService
import ru.vafeen.universityschedule.domain.scheduler.Scheduler
import ru.vafeen.universityschedule.domain.utils.SharedPreferencesValue

val networkRepositoryModule = module {
    single<DownloadFileRepository> {
        val downloadFileRepositoryImpl: DownloadFileRepositoryImpl by inject(clazz = DownloadFileRepositoryImpl::class.java)
        downloadFileRepositoryImpl
    }
    single<GoogleSheetsRepository> {
        val googleSheetsRepositoryImpl: GoogleSheetsRepositoryImpl by inject(clazz = GoogleSheetsRepositoryImpl::class.java)
        googleSheetsRepositoryImpl
    }
    single<ReleaseRepository> {
        val releaseRepositoryImpl: ReleaseRepositoryImpl by inject(clazz = ReleaseRepositoryImpl::class.java)
        releaseRepositoryImpl
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

