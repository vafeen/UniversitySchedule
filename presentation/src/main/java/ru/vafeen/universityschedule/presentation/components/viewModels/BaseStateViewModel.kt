package ru.vafeen.universityschedule.presentation.components.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseStateViewModel<State, Event> : ViewModel() {
    protected abstract val _state: MutableStateFlow<State>
    abstract val state: StateFlow<State>
    abstract fun sendEvent(event: Event)

    @Synchronized
    protected fun updateState(lambda: (State) -> State) {
        // здесь syncronized потому что у stateFlow не syncronized getter
        _state.value = lambda(_state.value)
    }
}