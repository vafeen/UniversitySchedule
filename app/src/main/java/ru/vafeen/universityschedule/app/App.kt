package ru.vafeen.universityschedule.app

import android.app.Application
import android.app.NotificationManager
import android.content.SharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.java.KoinJavaComponent.inject
import ru.vafeen.universityschedule.data.di.converterModule
import ru.vafeen.universityschedule.data.di.databaseModule
import ru.vafeen.universityschedule.data.di.databaseModuleImpl
import ru.vafeen.universityschedule.data.di.networkModule
import ru.vafeen.universityschedule.data.di.networkModuleImpl
import ru.vafeen.universityschedule.data.di.servicesModule
import ru.vafeen.universityschedule.data.di.servicesModuleImpl
import ru.vafeen.universityschedule.domain.di.databaseUseCaseModule
import ru.vafeen.universityschedule.domain.di.networkUseCaseModule
import ru.vafeen.universityschedule.domain.di.plannerUseCaseModule
import ru.vafeen.universityschedule.domain.notifications.NotificationChannelInfo
import ru.vafeen.universityschedule.domain.usecase.network.GetSheetDataAndUpdateDBUseCase
import ru.vafeen.universityschedule.domain.utils.createNotificationChannelKClass
import ru.vafeen.universityschedule.domain.utils.getSettingsOrCreateIfNull
import ru.vafeen.universityschedule.presentation.di.koinViewModelDIModule


class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                // presentation
                koinViewModelDIModule,
                // domain
                plannerUseCaseModule,
                networkUseCaseModule,
                databaseUseCaseModule,
                // data
                converterModule,
                networkModule,
                databaseModule,
                servicesModule,
                networkModuleImpl,
                databaseModuleImpl,
                servicesModuleImpl,
            )
        }
        val sharedPreferences: SharedPreferences by inject(clazz = SharedPreferences::class.java)
        val settings = sharedPreferences.getSettingsOrCreateIfNull()
        val getSheetDataAndUpdateDBUseCase:
                GetSheetDataAndUpdateDBUseCase by inject(clazz = GetSheetDataAndUpdateDBUseCase::class.java)
        CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
            settings.link?.let { link ->
                try {
                    getSheetDataAndUpdateDBUseCase.use(link)
                } catch (_: Exception) {
                }
            }
        }
        val notificationManager =
            applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(
            NotificationChannelInfo.About15MinutesBeforeLesson.createNotificationChannelKClass()
        )
        notificationManager.createNotificationChannel(
            NotificationChannelInfo.AfterStartingLesson.createNotificationChannelKClass()
        )
        notificationManager.createNotificationChannel(
            NotificationChannelInfo.ReminderRecovery.createNotificationChannelKClass()
        )
    }
}
