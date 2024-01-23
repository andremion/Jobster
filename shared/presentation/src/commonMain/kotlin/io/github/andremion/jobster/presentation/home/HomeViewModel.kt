package io.github.andremion.jobster.presentation.home

import io.github.andremion.jobster.domain.JobRepository
import io.github.andremion.jobster.domain.entity.SearchResult
import io.github.andremion.jobster.presentation.AbsViewModel
import io.github.andremion.jobster.presentation.WhileSubscribed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import moe.tlaster.precompose.viewmodel.viewModelScope

class HomeViewModel(
    jobRepository: JobRepository,
) : AbsViewModel<HomeUiState, HomeUiEvent, HomeUiEffect>() {

    private val jobs = jobRepository.getJobs()
    private val query = MutableStateFlow("")
    private val isSearchBarActive = MutableStateFlow(false)
    private val searchResults = query
        .debounce(QueryDebounceTimeoutInMillis)
        .distinctUntilChanged()
        .flatMapLatest(jobRepository::searchForContent)

    override val uiState: StateFlow<HomeUiState> = combine(
        jobs, query, isSearchBarActive, searchResults
    ) { jobs, query, isSearchBarActive, searchResults ->
        HomeUiState(
            isEmptyHintVisible = jobs.isEmpty(),
            query = query,
            isSearchBarActive = isSearchBarActive,
            searchResults = searchResults,
        )
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed,
        initialValue = HomeUiState()
    )

    override fun onUiEvent(uiEvent: HomeUiEvent) {
        when (uiEvent) {
            is HomeUiEvent.AddContentClick -> {
                mutableUiEffect.tryEmit(HomeUiEffect.NavigateToJobPostingSearch)
            }

            is HomeUiEvent.UpdateQuery -> {
                query.update { uiEvent.query }
            }

            is HomeUiEvent.UpdateSearchBarActive -> {
                query.update { "" }
                isSearchBarActive.update { uiEvent.isActive }
            }

            is HomeUiEvent.SearchBarBackClick -> {
                query.update { "" }
                isSearchBarActive.update { false }
            }

            is HomeUiEvent.ClearSearchClick -> {
                query.update { "" }
            }

            is HomeUiEvent.SearchResultClick -> {
                query.update { "" }
                isSearchBarActive.update { false }

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
