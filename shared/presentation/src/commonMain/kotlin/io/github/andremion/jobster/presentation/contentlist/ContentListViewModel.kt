package io.github.andremion.jobster.presentation.contentlist

import io.github.andremion.jobster.domain.JobRepository
import io.github.andremion.jobster.presentation.AbsViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import moe.tlaster.precompose.viewmodel.viewModelScope

class ContentListViewModel(
    jobRepository: JobRepository
) : AbsViewModel<ContentListUiState, ContentListUiEvent, ContentListUiEffect>(
    initialUiState = ContentListUiState()
) {

    init {
        jobRepository.getContents()
            .onStart {
                mutableUiState.update { uiState ->
                    uiState.copy(
                        isLoading = true,
                    )
                }
            }
            .onEach { contents ->
                mutableUiState.update { uiState ->
                    uiState.copy(
                        isLoading = false,
                        contents = contents,
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    override fun onUiEvent(uiEvent: ContentListUiEvent) {
        when (uiEvent) {
            is ContentListUiEvent.ContentClick -> {
                mutableUiEffect.tryEmit(ContentListUiEffect.NavigateToUrl(uiEvent.url))
            }
        }
    }
}
