package ru.vafeen.universityschedule.ui.components.viewModels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.vafeen.universityschedule.network.repository.NetworkRepository
import ru.vafeen.universityschedule.ui.components.viewModels.MainActivityViewModel
import javax.inject.Inject

class MainActivityViewModelFactory @Inject constructor(
    private val networkRepository: NetworkRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(networkRepository = networkRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}