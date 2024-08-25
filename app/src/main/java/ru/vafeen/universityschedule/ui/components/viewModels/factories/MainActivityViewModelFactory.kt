package ru.vafeen.universityschedule.ui.components.viewModels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.vafeen.universityschedule.network.service.DownloadService
import ru.vafeen.universityschedule.network.service.GitHubDataService
import ru.vafeen.universityschedule.ui.components.viewModels.MainActivityViewModel
import javax.inject.Inject

class MainActivityViewModelFactory @Inject constructor(
    private val gitHubDataService: GitHubDataService,
    private val downloadService: DownloadService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(
                gitHubDataService = gitHubDataService,
                downloadService = downloadService
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}