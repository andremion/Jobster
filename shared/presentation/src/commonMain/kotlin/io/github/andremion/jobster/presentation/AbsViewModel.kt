package io.github.andremion.jobster.presentation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import moe.tlaster.precompose.viewmodel.ViewModel

/**
 * Abstract [ViewModel] class that:
 * - Holds a [UiState] to be consumed by the UI and updated by the concrete implementation.
 * - Handles [UiEvent] from the UI.
 * - Holds [UiEffect] to be consumed by the UI and emitted by the concrete implementation.
 */
abstract class AbsViewModel<UiState, UiEvent, UiEffect>(initialUiState: UiState) : ViewModel() {

    protected val mutableUiState = MutableStateFlow(initialUiState)
    val uiState: StateFlow<UiState> = mutableUiState.asStateFlow()

    protected val mutableUiEffect = MutableSharedFlow<UiEffect>(
        // We need to buffer to allow emitting effects out of a suspend function.
        extraBufferCapacity = 1
    )
    val uiEffect: SharedFlow<UiEffect> = mutableUiEffect.asSharedFlow()

    abstract fun onUiEvent(uiEvent: UiEvent)
}
