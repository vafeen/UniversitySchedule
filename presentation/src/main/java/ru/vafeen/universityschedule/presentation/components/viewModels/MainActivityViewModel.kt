package ru.vafeen.universityschedule.presentation.components.viewModels

import androidx.lifecycle.ViewModel
import ru.vafeen.universityschedule.data.network.repository.NetworkRepository


internal class MainActivityViewModel(
    val networkRepository: NetworkRepository,
) : ViewModel() {
    var updateIsShowed = false
}