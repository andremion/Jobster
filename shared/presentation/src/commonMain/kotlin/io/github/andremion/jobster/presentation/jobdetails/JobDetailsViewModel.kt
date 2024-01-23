package io.github.andremion.jobster.presentation.jobdetails

import io.github.andremion.jobster.domain.JobRepository
import io.github.andremion.jobster.presentation.AbsViewModel
import io.github.andremion.jobster.presentation.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import moe.tlaster.precompose.viewmodel.viewModelScope

class JobDetailsViewModel(
    jobId: String,
    jobRepository: JobRepository
) : AbsViewModel<JobDetailsUiState, JobDetailsUiEvent, JobDetailsUiEffect>() {

    override val uiState: StateFlow<JobDetailsUiState> = jobRepository.getJobById(jobId)
        .map { job ->
            JobDetailsUiState(
                isLoading = false,
                job = job,
            )
        }.stateIn(
            scope = viewModelScope,
            started = WhileSubscribed,
            initialValue = JobDetailsUiState(isLoading = true)
        )

    override fun onUiEvent(uiEvent: JobDetailsUiEvent) {
        when (uiEvent) {
            is JobDetailsUiEvent.ContentClick -> {
                mutableUiEffect.tryEmit(JobDetailsUiEffect.NavigateToUrl(uiEvent.url))
            }
        }
    }
}
