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

package io.github.andremion.jobster.presentation.jobpostingsearch

import io.github.andremion.jobster.domain.JobRepository
import io.github.andremion.jobster.domain.entity.Job
import io.github.andremion.jobster.domain.entity.JobPosting
import io.github.andremion.jobster.domain.exception.JobPostingSearchException
import io.github.andremion.jobster.presentation.AbsViewModel
import io.github.andremion.jobster.presentation.WhileSubscribed
import io.github.andremion.jobster.presentation.jobpostingsearch.mapper.transform
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.viewModelScope

class JobPostingSearchViewModel(
    private val jobRepository: JobRepository,
) : AbsViewModel<JobPostingSearchUiState, JobPostingSearchUiEvent, JobPostingSearchUiEffect>() {

    private var searchJob: kotlinx.coroutines.Job? = null

    private val mutableUiState = MutableStateFlow(JobPostingSearchUiState())

    override val uiState: StateFlow<JobPostingSearchUiState> = mutableUiState
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed,
            initialValue = JobPostingSearchUiState(isSearchBarActive = true)
        )

    override fun onUiEvent(uiEvent: JobPostingSearchUiEvent) {
        when (uiEvent) {
            is JobPostingSearchUiEvent.BackClick -> {
                mutableUiEffect.tryEmit(JobPostingSearchUiEffect.NavigateBack)
            }

            is JobPostingSearchUiEvent.UpdateUrl -> {
                searchJob?.cancel()
                mutableUiState.update { uiState ->
                    uiState.copy(
                        url = uiEvent.url,
                        isLoading = false,
                        error = null,
                    )
                }
            }

            is JobPostingSearchUiEvent.UpdateSearchBarActive -> {
                searchJob?.cancel()
                mutableUiState.update { uiState ->
                    uiState.copy(
                        isSearchBarActive = uiEvent.isActive,
                        isLoading = false,
                        error = null,
                    )
                }
            }

            is JobPostingSearchUiEvent.SearchBarBackClick -> {
                searchJob?.cancel()
                mutableUiState.update { uiState ->
                    uiState.copy(
                        isSearchBarActive = false,
                        isLoading = false,
                        error = null,
                    )
                }
            }

            is JobPostingSearchUiEvent.SearchBarClearClick -> {
                searchJob?.cancel()
                mutableUiState.update { uiState ->
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
                    mutableUiState.update { uiState ->
                        uiState.copy(
                            isLoading = true,
                            jobPosting = null,
                            error = null,
                        )
                    }
                    searchJob?.cancel()
                    searchJob = viewModelScope.launch {
                        runCatching {
                            val jobPosting = jobRepository.searchJobPosting(url)
                            val contentIds = jobPosting.contents.map(JobPosting.Content::url)
                            val existingContentIds = jobRepository.getContentsByIds(contentIds)
                                .firstOrNull()?.map(Job.Content::id) ?: emptyList()
                            jobPosting.transform(existingContentIds)
                        }.onSuccess { jobPosting ->
                            mutableUiState.update { uiState ->
                                uiState.copy(
                                    isSearchBarActive = false,
                                    isLoading = false,
                                    jobPosting = jobPosting,
                                )
                            }
                        }.onFailure { cause ->
                            cause.printStackTrace()
                            mutableUiState.update { uiState ->
                                uiState.copy(
                                    isLoading = false,
                                    error = cause as? JobPostingSearchException,
                                )
                            }
                        }
                    }
                }
            }

            is JobPostingSearchUiEvent.ContentTitleClick -> {
                mutableUiEffect.tryEmit(JobPostingSearchUiEffect.NavigateToUrl(uiEvent.url))
            }

            is JobPostingSearchUiEvent.ContentSwitchClick -> {
                viewModelScope.launch {
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
                    mutableUiState.update { uiState ->
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
