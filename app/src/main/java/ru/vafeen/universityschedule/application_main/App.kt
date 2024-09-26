package ru.vafeen.universityschedule.application_main

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.vafeen.reminder.noui.di.koinDatabaseDIModule
import ru.vafeen.universityschedule.noui.di.koinNetworkDIModule
import ru.vafeen.universityschedule.noui.di.koinDIModule
import ru.vafeen.universityschedule.noui.di.koinViewModelDIModule
import ru.vafeen.universityschedule.noui.notifications.NotificationChannelInfo


class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                koinDatabaseDIModule,
                koinNetworkDIModule,
                koinDIModule,
                koinViewModelDIModule,
                )
        }

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationChannel = NotificationChannel(
            NotificationChannelInfo.NOTIFICATION_CHANNEL_ID,
            NotificationChannelInfo.NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(notificationChannel)
    }
}