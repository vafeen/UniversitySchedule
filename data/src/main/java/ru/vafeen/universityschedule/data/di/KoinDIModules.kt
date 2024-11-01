package ru.vafeen.universityschedule.data.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.vafeen.universityschedule.data.database.DTConverters

val servicesModule = module {
    singleOf(::DTConverters)
}