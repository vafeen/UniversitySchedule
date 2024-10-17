package ru.vafeen.universityschedule.last.ui.components.viewModels

import androidx.lifecycle.ViewModel
import ru.vafeen.universityschedule.last.network.repository.NetworkRepository


class MainActivityViewModel(
    val networkRepository: NetworkRepository,
) : ViewModel() {
    var updateIsShowed = false
}