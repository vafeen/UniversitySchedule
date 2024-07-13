package ru.vafeen.universityschedule.ui.components.viewModels.factories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.vafeen.universityschedule.database.DatabaseRepository
import ru.vafeen.universityschedule.ui.components.viewModels.MainScreenViewModel
import javax.inject.Inject

class MainScreenViewModelFactory @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    @ApplicationContext private val context: Context
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainScreenViewModel::class.java)) {
            return MainScreenViewModel(
                databaseRepository = databaseRepository,
                context = context
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

