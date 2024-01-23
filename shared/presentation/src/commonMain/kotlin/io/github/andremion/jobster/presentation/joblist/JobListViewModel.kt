package io.github.andremion.jobster.presentation.joblist

import io.github.andremion.jobster.domain.JobRepository
import io.github.andremion.jobster.presentation.AbsViewModel
import io.github.andremion.jobster.presentation.WhileSubscribed
import io.github.andremion.jobster.presentation.joblist.mapper.transform
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import moe.tlaster.precompose.viewmodel.viewModelScope

class JobListViewModel(
    jobRepository: JobRepository
) : AbsViewModel<JobListUiState, JobListUiEvent, JobListUiEffect>() {

    override val uiState: StateFlow<JobListUiState> = jobRepository.getJobsWithContents()
        .map { jobs ->
            JobListUiState(
                isLoading = false,
                jobs = jobs.transform(),
            )
        }.stateIn(
            scope = viewModelScope,
            started = WhileSubscribed,
            initialValue = JobListUiState(isLoading = true)
        )

    override fun onUiEvent(uiEvent: JobListUiEvent) {
        when (uiEvent) {
            is JobListUiEvent.JobClick -> {
                mutableUiEffect.tryEmit(JobListUiEffect.NavigateToJobDetails(uiEvent.jobId))
            }
        }
    }
}
