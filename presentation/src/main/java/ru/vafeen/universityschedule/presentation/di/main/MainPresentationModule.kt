package ru.vafeen.universityschedule.presentation.di.main

import org.koin.dsl.module
import ru.vafeen.universityschedule.presentation.di.koinViewModelDIModule
import ru.vafeen.universityschedule.presentation.di.mainActivityIntentProviderDIModule

val mainPresentationModule = module {
    includes(koinViewModelDIModule, mainActivityIntentProviderDIModule)
}