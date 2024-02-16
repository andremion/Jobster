/*
 *    Copyright 2024. André Luiz Oliveira Rêgo
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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
