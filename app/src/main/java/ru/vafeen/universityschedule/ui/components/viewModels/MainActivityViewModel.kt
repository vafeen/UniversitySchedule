package ru.vafeen.universityschedule.ui.components.viewModels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.vafeen.universityschedule.network.repository.NetworkRepository
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    val networkRepository: NetworkRepository,
) : ViewModel() {
    var updateIsShowed = false
}