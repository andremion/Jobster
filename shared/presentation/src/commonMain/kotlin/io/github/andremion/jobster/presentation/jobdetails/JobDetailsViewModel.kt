package io.github.andremion.jobster.presentation.jobdetails

import io.github.andremion.jobster.domain.JobRepository
import io.github.andremion.jobster.presentation.AbsViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import moe.tlaster.precompose.viewmodel.viewModelScope

class JobDetailsViewModel(
    jobId: String,
    jobRepository: JobRepository
) : AbsViewModel<JobDetailsUiState, JobDetailsUiEvent, JobDetailsUiEffect>(
    initialUiState = JobDetailsUiState()
) {

    init {
        mutableUiState.update { uiState ->
            uiState.copy(
                isLoading = true,
            )
        }
        jobRepository.getJobById(jobId)
            .onEach { job ->
                mutableUiState.update { uiState ->
                    uiState.copy(
                        isLoading = false,
                        job = job,
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    override fun onUiEvent(uiEvent: JobDetailsUiEvent) {
        when (uiEvent) {
            is JobDetailsUiEvent.ContentClick -> {
                mutableUiEffect.tryEmit(JobDetailsUiEffect.NavigateToUrl(uiEvent.url))
            }
        }
    }

}
