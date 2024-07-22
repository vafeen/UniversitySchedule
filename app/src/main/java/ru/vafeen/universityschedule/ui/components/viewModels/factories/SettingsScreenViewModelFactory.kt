package ru.vafeen.universityschedule.ui.components.viewModels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.vafeen.universityschedule.database.DatabaseRepository
import ru.vafeen.universityschedule.ui.components.viewModels.SettingsScreenViewModel
import ru.vafeen.universityschedule.utils.SharedPreferences
import javax.inject.Inject

class SettingsScreenViewModelFactory @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    val sharedPreferences: SharedPreferences
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsScreenViewModel::class.java)) {
            return SettingsScreenViewModel(
                databaseRepository = databaseRepository,
                sharedPreferences = sharedPreferences
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}