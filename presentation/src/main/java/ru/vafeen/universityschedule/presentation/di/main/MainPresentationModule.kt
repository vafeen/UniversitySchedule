package ru.vafeen.universityschedule.presentation.di.main

import org.koin.dsl.module
import ru.vafeen.universityschedule.presentation.di.koinViewModelDIModule

val mainPresentationModule = module {
    includes(koinViewModelDIModule)
}