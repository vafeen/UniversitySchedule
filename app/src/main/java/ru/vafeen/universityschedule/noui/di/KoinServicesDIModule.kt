package ru.vafeen.universityschedule.noui.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.vafeen.universityschedule.database.DTConverters
import ru.vafeen.universityschedule.noui.notifications.NotificationService
import ru.vafeen.universityschedule.noui.planner.Scheduler
import ru.vafeen.universityschedule.noui.shared_preferences.SharedPreferences

val koinDIModule = module {
    singleOf(::NotificationService)
    singleOf(::Scheduler)
    singleOf(::DTConverters)
    singleOf(::SharedPreferences)
}