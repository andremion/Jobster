package io.github.andremion.jobster.presentation.joblist

import io.github.andremion.jobster.domain.JobRepository
import io.github.andremion.jobster.presentation.AbsViewModel
import io.github.andremion.jobster.presentation.joblist.mapper.transform
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import moe.tlaster.precompose.viewmodel.viewModelScope

class JobListViewModel(
    jobRepository: JobRepository
) : AbsViewModel<JobListUiState, JobListUiEvent, JobListUiEffect>(
    initialUiState = JobListUiState()
) {

    init {
        jobRepository.getJobsWithContents()
            .onStart {
                mutableUiState.update { uiState ->
                    uiState.copy(
                        isLoading = true,
                    )
                }
            }
            .onEach { jobs ->
                mutableUiState.update { uiState ->
                    uiState.copy(
                        isLoading = false,
                        jobs = jobs.transform(),
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    override fun onUiEvent(uiEvent: JobListUiEvent) {
        when (uiEvent) {
            is JobListUiEvent.JobClick -> {
                mutableUiEffect.tryEmit(JobListUiEffect.NavigateToJobDetails(uiEvent.jobId))
            }
        }
    }
}
