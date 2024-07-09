package ru.vafeen.universityschedule.ui.components.viewModels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.vafeen.universityschedule.database.dao.DatabaseRepository
import ru.vafeen.universityschedule.ui.components.viewModels.MainScreenViewModel
import javax.inject.Inject

class MainScreenViewModelFactory @Inject constructor(private val databaseRepository: DatabaseRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainScreenViewModel::class.java)) {
            return MainScreenViewModel(databaseRepository = databaseRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

