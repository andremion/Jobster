package io.github.andremion.jobster.presentation.home

import io.github.andremion.jobster.domain.JobRepository
import io.github.andremion.jobster.domain.entity.SearchResult
import io.github.andremion.jobster.presentation.AbsViewModel
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.viewModelScope

class HomeViewModel(
    jobRepository: JobRepository,
) : AbsViewModel<HomeUiState, HomeUiEvent, HomeUiEffect>(
    initialUiState = HomeUiState()
) {

    init {
        viewModelScope.launch {
            val jobs = jobRepository.getJobs().firstOrNull() ?: emptyList()
            mutableUiState.update { uiState ->
                uiState.copy(isEmptyHintVisible = jobs.isEmpty())
            }
        }
        uiState
            .map { state -> state.query }
            .debounce(QueryDebounceTimeoutInMillis)
            .distinctUntilChanged()
            .flatMapLatest(jobRepository::searchForContent)
            .onEach { results ->
                mutableUiState.update { uiState ->
                    uiState.copy(searchResults = results)
                }
            }
            .launchIn(viewModelScope)
    }

    override fun onUiEvent(uiEvent: HomeUiEvent) {
        when (uiEvent) {
            is HomeUiEvent.AddContentClick -> {
                mutableUiEffect.tryEmit(HomeUiEffect.NavigateToJobPostingSearch)
            }

            is HomeUiEvent.UpdateQuery -> {
                mutableUiState.update { uiState ->
                    uiState.copy(query = uiEvent.query)
                }
            }

            is HomeUiEvent.UpdateSearchBarActive -> {
                mutableUiState.update { uiState ->
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
                mutableUiState.update { uiState ->
                    uiState.copy(
                        query = "",
                        isSearchBarActive = false
                    )
                }
            }

            is HomeUiEvent.ClearSearchClick -> {
                mutableUiState.update { uiState ->
                    uiState.copy(query = "")
                }
            }

            is HomeUiEvent.SearchResultClick -> {
                mutableUiState.update { uiState ->
                    uiState.copy(
                        query = "",
                        isSearchBarActive = false
                    )
                }
                when (uiEvent.type) {
                    SearchResult.Type.Job -> {
                        mutableUiEffect.tryEmit(
                            HomeUiEffect.NavigateToJobDetails(
                                jobId = uiEvent.id,
                            )
                        )
                    }

                    SearchResult.Type.Content -> {
                        mutableUiEffect.tryEmit(
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

private const val QueryDebounceTimeoutInMillis = 300L
