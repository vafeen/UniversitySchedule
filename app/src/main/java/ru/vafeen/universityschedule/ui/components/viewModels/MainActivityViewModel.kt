package ru.vafeen.universityschedule.ui.components.viewModels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.vafeen.universityschedule.network.service.GitHubDataService
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    val gitHubDataService: GitHubDataService
) : ViewModel() {
    var updateIsShowed = false
}