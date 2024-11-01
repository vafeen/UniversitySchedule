package ru.vafeen.universityschedule.presentation.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.vafeen.universityschedule.presentation.components.viewModels.MainActivityViewModel
import ru.vafeen.universityschedule.presentation.components.viewModels.MainScreenViewModel
import ru.vafeen.universityschedule.presentation.components.viewModels.SettingsScreenViewModel


val koinViewModelDIModule = module {
    viewModelOf(::MainActivityViewModel)
    viewModelOf(::MainScreenViewModel)
    viewModelOf(::SettingsScreenViewModel)
}