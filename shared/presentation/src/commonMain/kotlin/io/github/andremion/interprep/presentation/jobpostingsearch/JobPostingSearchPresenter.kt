package io.github.andremion.interprep.presentation.jobpostingsearch

import io.github.andremion.boomerang.AbsPresenter
import io.github.andremion.interprep.domain.JobRepository
import io.github.andremion.interprep.domain.entity.Job
import io.github.andremion.interprep.domain.entity.JobPosting
import io.github.andremion.interprep.presentation.jobpostingsearch.mapper.transform
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class JobPostingSearchPresenter(
    private val jobRepository: JobRepository,
) : AbsPresenter<JobPostingSearchUiState, JobPostingSearchUiEvent, JobPostingSearchUiEffect>(JobPostingSearchUiState.Initial) {

    private var searchJob: kotlinx.coroutines.Job? = null

    override fun onUiEvent(uiEvent: JobPostingSearchUiEvent) {
        when (uiEvent) {
            is JobPostingSearchUiEvent.Init -> {
                updateUiState { uiState ->
                    uiState.copy(
                        isSearchBarActive = true,
                    )
                }
            }

            is JobPostingSearchUiEvent.UpdateUrl -> {
                searchJob?.cancel()
                updateUiState { uiState ->
                    uiState.copy(
                        url = uiEvent.url,
                        isLoading = false,
                        error = null,
                    )
                }
            }

            is JobPostingSearchUiEvent.UpdateSearchBarActive -> {
                searchJob?.cancel()
                updateUiState { uiState ->
                    uiState.copy(
                        isSearchBarActive = uiEvent.isActive,
                        isLoading = false,
                        error = null,
                    )
                }
            }

            is JobPostingSearchUiEvent.SearchBarBackClick -> {
                searchJob?.cancel()
                updateUiState { uiState ->
                    uiState.copy(
                        isSearchBarActive = false,
                        isLoading = false,
                        error = null,
                    )
                }
            }

            is JobPostingSearchUiEvent.ClearSearchClick -> {
                searchJob?.cancel()
                updateUiState { uiState ->
                    uiState.copy(
                        url = "",
                        isLoading = false,
                        error = null,
                    )
                }
            }

            is JobPostingSearchUiEvent.SearchClick -> {
                val url = uiState.value.url
                if (url.isNotBlank()) {
                    updateUiState { uiState ->
                        uiState.copy(
                            isLoading = true,
                            jobPosting = null,
                            error = null,
                        )
                    }
                    searchJob?.cancel()
                    searchJob = presenterScope.launch {
                        try {
                            val jobPosting = jobRepository.searchJobPosting(url)
                            val contentIds = jobPosting.contents.map(JobPosting.Content::url)
                            val existingContentIds = jobRepository.getContentsByIds(contentIds)
                                .firstOrNull()?.map(Job.Content::id) ?: emptyList()
                            updateUiState { uiState ->
                                uiState.copy(
                                    isSearchBarActive = false,
                                    isLoading = false,
                                    jobPosting = jobPosting.transform(existingContentIds),
                                )
                            }
                        } catch (cause: Throwable) {
                            updateUiState { uiState ->
                                uiState.copy(
                                    isLoading = false,
                                    error = cause,
                                )
                            }
                        }
                    }
                }
            }

            is JobPostingSearchUiEvent.ContentTitleClick -> {
                uiEffect.tryEmit(JobPostingSearchUiEffect.NavigateToUrl(uiEvent.url))
            }

            is JobPostingSearchUiEvent.ContentSwitchClick -> {
                presenterScope.launch {
                    val state = uiState.value
                    val jobPosting = requireNotNull(state.jobPosting)
                    if (uiEvent.isChecked) {
                        jobRepository.save(
                            jobPosting = jobPosting.transform(state.url),
                            contents = listOf(uiEvent.content.transform())
                        )
                    } else {
                        jobRepository.delete(
                            jobId = state.url,
                            contentId = uiEvent.content.url
                        )
                    }
                    updateUiState { uiState ->
                        uiState.copy(
                            jobPosting = jobPosting.copy(
                                contents = jobPosting.contents.map { content ->
                                    if (content.url == uiEvent.content.url) {
                                        content.copy(isChecked = uiEvent.isChecked)
                                    } else {
                                        content
                                    }
                                }
                            )
                        )
                    }
                }
            }
        }
    }
}
