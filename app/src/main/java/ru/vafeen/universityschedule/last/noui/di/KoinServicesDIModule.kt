package ru.vafeen.universityschedule.last.noui.di

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.vafeen.universityschedule.last.database.DTConverters
import ru.vafeen.universityschedule.last.noui.notifications.NotificationService
import ru.vafeen.universityschedule.last.noui.planner.Scheduler
import ru.vafeen.universityschedule.last.noui.shared_preferences.SharedPreferencesValue

val koinDIModule = module {
    singleOf(::NotificationService)
    singleOf(::Scheduler)
    singleOf(::DTConverters)
    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            SharedPreferencesValue.Name.key, Context.MODE_PRIVATE
        )
    }
}