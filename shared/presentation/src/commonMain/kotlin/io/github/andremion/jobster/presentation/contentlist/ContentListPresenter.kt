package io.github.andremion.jobster.presentation.contentlist

import io.github.andremion.boomerang.AbsPresenter
import io.github.andremion.jobster.domain.JobRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ContentListPresenter(
    private val jobRepository: JobRepository
) : AbsPresenter<ContentListUiState, ContentListUiEvent, ContentListUiEffect>(ContentListUiState.Initial) {

    override fun onUiEvent(uiEvent: ContentListUiEvent) {
        when (uiEvent) {
            ContentListUiEvent.Init -> {
                updateUiState { uiState ->
                    uiState.copy(
                        isLoading = true,
                    )
                }
                jobRepository.getContents()
                    .onEach { contents ->
                        updateUiState { uiState ->
                            uiState.copy(
                                isLoading = false,
                                contents = contents,
                                error = null
                            )
                        }
                    }
                    .launchIn(presenterScope)
            }

            is ContentListUiEvent.ContentClick -> {
                uiEffect.tryEmit(ContentListUiEffect.NavigateToUrl(uiEvent.url))
            }
        }
    }
}
