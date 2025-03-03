package ru.vafeen.universityschedule.presentation.components.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class BaseStateViewModel<State, Event> : ViewModel() {
    abstract val state: StateFlow<State>
    abstract fun sendEvent(event: Event)
    protected abstract fun updateState(lambda: (State) -> State)
}