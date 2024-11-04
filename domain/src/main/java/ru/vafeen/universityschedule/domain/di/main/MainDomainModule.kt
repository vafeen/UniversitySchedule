package ru.vafeen.universityschedule.domain.di.main

import org.koin.dsl.module
import ru.vafeen.universityschedule.domain.di.databaseUseCaseModule
import ru.vafeen.universityschedule.domain.di.networkUseCaseModule
import ru.vafeen.universityschedule.domain.di.plannerUseCaseModule

val mainDomainModule = module {
    includes(
        plannerUseCaseModule,
        networkUseCaseModule,
        databaseUseCaseModule
    )
}