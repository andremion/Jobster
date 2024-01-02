package io.github.andremion.interprep.presentation.contentlist

import io.github.andremion.boomerang.AbsPresenter
import io.github.andremion.interprep.domain.JobRepository
import io.github.andremion.interprep.presentation.contentlist.mapper.transform
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
                                contents = contents.transform(),
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
