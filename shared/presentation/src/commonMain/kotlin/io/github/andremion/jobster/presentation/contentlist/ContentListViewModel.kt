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

package io.github.andremion.jobster.presentation.contentlist

import io.github.andremion.jobster.domain.JobRepository
import io.github.andremion.jobster.presentation.AbsViewModel
import io.github.andremion.jobster.presentation.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.viewModelScope

class ContentListViewModel(
    private val jobRepository: JobRepository
) : AbsViewModel<ContentListUiState, ContentListUiEvent, ContentListUiEffect>() {

    override val uiState: StateFlow<ContentListUiState> = jobRepository.getContents()
        .map { contents ->
            ContentListUiState(
                isLoading = false,
                contents = contents,
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed,
            initialValue = ContentListUiState(isLoading = true)
        )

    override fun onUiEvent(uiEvent: ContentListUiEvent) {
        when (uiEvent) {
            is ContentListUiEvent.ContentClick -> {
                mutableUiEffect.tryEmit(ContentListUiEffect.NavigateToUrl(uiEvent.url))
            }

            is ContentListUiEvent.DeleteContent -> {
                viewModelScope.launch {
                    jobRepository.delete(uiEvent.contentId)
                }
            }
        }
    }
}
