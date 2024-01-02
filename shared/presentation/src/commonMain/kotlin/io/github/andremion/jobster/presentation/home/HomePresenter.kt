package io.github.andremion.jobster.presentation.home

import io.github.andremion.boomerang.AbsPresenter
import io.github.andremion.jobster.domain.JobRepository
import io.github.andremion.jobster.domain.entity.SearchResult
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class HomePresenter(
    private val jobRepository: JobRepository,
) : AbsPresenter<HomeUiState, HomeUiEvent, HomeUiEffect>(HomeUiState.Initial) {

    override fun onUiEvent(uiEvent: HomeUiEvent) {
        when (uiEvent) {
            is HomeUiEvent.Init -> {
                uiState
                    .map { state -> state.query }
                    .debounce(DebounceTimeoutInMillis)
                    .distinctUntilChanged()
                    .flatMapLatest(jobRepository::searchForContent)
                    .onEach { results ->
                        updateUiState { uiState ->
                            uiState.copy(searchResults = results)
                        }
                    }
                    .launchIn(presenterScope)
            }

            is HomeUiEvent.AddContentClick -> {
                uiEffect.tryEmit(HomeUiEffect.NavigateToJobPostingSearch)
            }

            is HomeUiEvent.UpdateQuery -> {
                updateUiState { uiState ->
                    uiState.copy(query = uiEvent.query)
                }
            }

            is HomeUiEvent.UpdateSearchBarActive -> {
                updateUiState { uiState ->
                    uiState.copy(
                        query = if (uiEvent.isActive) {
                            uiState.query
                        } else {
                            ""
                        },
                        isSearchBarActive = uiEvent.isActive,
                    )
                }
            }

            is HomeUiEvent.SearchBarBackClick -> {
                updateUiState { uiState ->
                    uiState.copy(
                        query = "",
                        isSearchBarActive = false
                    )
                }
            }

            is HomeUiEvent.ClearSearchClick -> {
                updateUiState { uiState ->
                    uiState.copy(query = "")
                }
            }

            is HomeUiEvent.SearchResultClick -> {
                updateUiState { uiState ->
                    uiState.copy(
                        query = "",
                        isSearchBarActive = false
                    )
                }
                when (uiEvent.type) {
                    SearchResult.Type.Job -> {
                        uiEffect.tryEmit(
                            HomeUiEffect.NavigateToJobDetails(
                                jobId = uiEvent.id,
                            )
                        )
                    }

                    SearchResult.Type.Content -> {
                        uiEffect.tryEmit(
                            HomeUiEffect.NavigateToUrl(
                                url = uiEvent.url,
                            )
                        )
                    }
                }
            }
        }
    }
}

private const val DebounceTimeoutInMillis = 300L