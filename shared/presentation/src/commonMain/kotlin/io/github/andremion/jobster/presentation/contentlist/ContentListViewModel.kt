package io.github.andremion.jobster.presentation.contentlist

import io.github.andremion.jobster.domain.JobRepository
import io.github.andremion.jobster.presentation.AbsViewModel
import io.github.andremion.jobster.presentation.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import moe.tlaster.precompose.viewmodel.viewModelScope

class ContentListViewModel(
    jobRepository: JobRepository
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
        }
    }
}
