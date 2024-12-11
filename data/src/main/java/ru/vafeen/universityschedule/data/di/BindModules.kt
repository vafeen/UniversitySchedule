package ru.vafeen.universityschedule.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.universityschedule.data.database.AppDatabase
import ru.vafeen.universityschedule.data.database.AppDatabaseMigrationManager
import ru.vafeen.universityschedule.data.impl.database.LessonRepositoryImpl
import ru.vafeen.universityschedule.data.impl.database.ReminderRepositoryImpl
import ru.vafeen.universityschedule.data.impl.network.repository.DownloadFileRepositoryImpl
import ru.vafeen.universityschedule.data.impl.network.repository.ReleaseRepositoryImpl
import ru.vafeen.universityschedule.data.impl.network.repository.SheetDataRepositoryImpl
import ru.vafeen.universityschedule.data.impl.network.service.ApkDownloaderImpl
import ru.vafeen.universityschedule.data.impl.network.service.ApkInstallerImpl
import ru.vafeen.universityschedule.data.impl.notifications.NotificationBuilderImpl
import ru.vafeen.universityschedule.data.impl.notifications.NotificationServiceImpl
import ru.vafeen.universityschedule.data.impl.scheduler.SchedulerAPIMigrationManagerImpl
import ru.vafeen.universityschedule.data.impl.scheduler.SchedulerImpl
import ru.vafeen.universityschedule.domain.database.LessonRepository
import ru.vafeen.universityschedule.domain.database.ReminderRepository
import ru.vafeen.universityschedule.domain.network.repository.DownloadFileRepository
import ru.vafeen.universityschedule.domain.network.repository.ReleaseRepository
import ru.vafeen.universityschedule.domain.network.repository.SheetDataRepository
import ru.vafeen.universityschedule.domain.network.service.ApkDownloader
import ru.vafeen.universityschedule.domain.network.service.ApkInstaller
import ru.vafeen.universityschedule.domain.notifications.NotificationBuilder
import ru.vafeen.universityschedule.domain.notifications.NotificationService
import ru.vafeen.universityschedule.domain.scheduler.Scheduler
import ru.vafeen.universityschedule.domain.scheduler.SchedulerAPIMigrationManager
import ru.vafeen.universityschedule.domain.utils.SharedPreferencesValue

internal val networkRepositoryModule = module {
    single<DownloadFileRepository> {
        val downloadFileRepositoryImpl: DownloadFileRepositoryImpl by inject(clazz = DownloadFileRepositoryImpl::class.java)
        downloadFileRepositoryImpl
    }
    single<SheetDataRepository> {
        val googleSheetsRepositoryImpl: SheetDataRepositoryImpl by inject(clazz = SheetDataRepositoryImpl::class.java)
        googleSheetsRepositoryImpl
    }
    single<ReleaseRepository> {
        val releaseRepositoryImpl: ReleaseRepositoryImpl by inject(clazz = ReleaseRepositoryImpl::class.java)
        releaseRepositoryImpl
    }
}


internal val databaseModule = module {
    single<LessonRepository> {
        val lessonRepositoryImpl: LessonRepositoryImpl by inject(clazz = LessonRepositoryImpl::class.java)
        lessonRepositoryImpl
    }

    single<ReminderRepository> {
        val reminderRepositoryImpl: ReminderRepositoryImpl by inject(clazz = ReminderRepositoryImpl::class.java)
        reminderRepositoryImpl
    }
    single<AppDatabase> {
        Room.databaseBuilder(
            context = get(), klass = AppDatabase::class.java, name = "universityScheduleDB.db"
        ).addMigrations(*AppDatabaseMigrationManager().migrations()).build()
    }
}
internal val servicesModule = module {
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
    singleOf(::SchedulerAPIMigrationManagerImpl) {
        bind<SchedulerAPIMigrationManager>()
    }
//    singleOf(::SettingsManagerImpl) {
//        bind<SettingsManager>()
//    }
}

