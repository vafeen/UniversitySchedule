package ru.vafeen.universityschedule.data.di.main

import org.koin.dsl.module
import ru.vafeen.universityschedule.data.di.converterModule
import ru.vafeen.universityschedule.data.di.databaseModuleImpl
import ru.vafeen.universityschedule.data.di.networkRepositoryModuleImpl
import ru.vafeen.universityschedule.data.di.networkServiceModuleImpl
import ru.vafeen.universityschedule.data.di.servicesModule
import ru.vafeen.universityschedule.data.di.servicesModuleImpl

val mainDataModule = module {
    includes(
        databaseModuleImpl,
        servicesModule,
        networkRepositoryModuleImpl,
        networkServiceModuleImpl,
        databaseModuleImpl,
        servicesModuleImpl,
        converterModule,
    )
}