package io.github.andremion.jobster.presentation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import moe.tlaster.precompose.viewmodel.ViewModel

internal val WhileSubscribed = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000)

/**
 * Abstract [ViewModel] class that:
 * - Holds a [UiState] to be consumed by the UI and updated by the concrete implementation.
 * - Handles [UiEvent] from the UI.
 * - Holds [UiEffect] to be consumed by the UI and emitted by the concrete implementation.
 */
abstract class AbsViewModel<UiState, UiEvent, UiEffect> : ViewModel() {

    abstract val uiState: StateFlow<UiState>

    protected val mutableUiEffect = MutableSharedFlow<UiEffect>(
        // We need to buffer to allow emitting effects out of a suspend function.
        extraBufferCapacity = 1
    )
    val uiEffect: SharedFlow<UiEffect> = mutableUiEffect.asSharedFlow()

    abstract fun onUiEvent(uiEvent: UiEvent)
}
