package ru.vafeen.universityschedule.ui.components.viewModels

import androidx.lifecycle.ViewModel
import ru.vafeen.universityschedule.network.repository.NetworkRepository


class MainActivityViewModel(
    val networkRepository: NetworkRepository,
) : ViewModel() {
    var updateIsShowed = false
}