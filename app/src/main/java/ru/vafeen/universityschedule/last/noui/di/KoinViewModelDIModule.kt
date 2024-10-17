package ru.vafeen.universityschedule.last.last.last.noui.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.vafeen.universityschedule.last.ui.components.viewModels.MainActivityViewModel
import ru.vafeen.universityschedule.last.ui.components.viewModels.MainScreenViewModel
import ru.vafeen.universityschedule.last.ui.components.viewModels.SettingsScreenViewModel

val koinViewModelDIModule = module {
    viewModelOf(::MainActivityViewModel)
    viewModelOf(::MainScreenViewModel)
    viewModelOf(::SettingsScreenViewModel)
}