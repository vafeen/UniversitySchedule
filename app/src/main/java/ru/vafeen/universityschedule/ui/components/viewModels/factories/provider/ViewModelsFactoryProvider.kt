package ru.vafeen.universityschedule.ui.components.viewModels.factories.provider

import ru.vafeen.universityschedule.ui.components.viewModels.factories.MainScreenViewModelFactory
import ru.vafeen.universityschedule.ui.components.viewModels.factories.SettingsScreenViewModelFactory
import javax.inject.Inject

class ViewModelsFactoryProvider @Inject constructor(
    val mainScreenViewModelFactory: MainScreenViewModelFactory,
    val settingsScreenViewModelFactory: SettingsScreenViewModelFactory
)