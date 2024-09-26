package ru.vafeen.universityschedule.noui.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.vafeen.universityschedule.ui.components.viewModels.MainActivityViewModel
import ru.vafeen.universityschedule.ui.components.viewModels.MainScreenViewModel
import ru.vafeen.universityschedule.ui.components.viewModels.SettingsScreenViewModel

val koinViewModelDIModule = module {
    viewModelOf(::MainActivityViewModel)
    viewModelOf(::MainScreenViewModel)
    viewModelOf(::SettingsScreenViewModel)
}