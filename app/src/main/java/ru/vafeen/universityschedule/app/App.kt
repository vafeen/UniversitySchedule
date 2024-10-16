package ru.vafeen.universityschedule.app

import android.app.Application
import android.app.NotificationManager
import android.content.SharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import ru.vafeen.universityschedule.data.di.koinDatabaseDIModule
import ru.vafeen.universityschedule.data.di.koinNetworkDIModule
import ru.vafeen.universityschedule.data.utils.cleverUpdatingLessons
import ru.vafeen.universityschedule.data.utils.createGSheetsService
import ru.vafeen.universityschedule.data.utils.getLessonsListFromGSheetsTable
import ru.vafeen.universityschedule.domain.di.databaseDomainModule
import ru.vafeen.universityschedule.domain.di.koinServicesDIModule
import ru.vafeen.universityschedule.domain.di.networkDomainModule
import ru.vafeen.universityschedule.domain.notifications.NotificationChannelInfo
import ru.vafeen.universityschedule.domain.notifications.createNotificationChannelKClass
import ru.vafeen.universityschedule.domain.utils.getSettingsOrCreateIfNull
import ru.vafeen.universityschedule.presentation.koinViewModelDIModule


class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                koinDatabaseDIModule,
                koinNetworkDIModule,
                koinServicesDIModule,
                koinViewModelDIModule,
                databaseDomainModule,
                networkDomainModule
            )
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

        val sharedPreferences: SharedPreferences by inject()
        val settings = sharedPreferences.getSettingsOrCreateIfNull()
        CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {

            settings.link?.let { link ->
                try {
                    createGSheetsService(link = link)
                        ?.getLessonsListFromGSheetsTable()
                        ?.let {
                            cleverUpdatingLessons(newLessons = it)
                        }
                } catch (_: Exception) {
                }
            }
        }
    }
}
