package ru.vafeen.universityschedule.data.di

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.vafeen.universityschedule.domain.utils.SharedPreferencesValue


internal val servicesModule = module {
    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            SharedPreferencesValue.Name.key, Context.MODE_PRIVATE
        )
    }
}

