package io.github.andremion.jobster.presentation.jobdetails

import io.github.andremion.boomerang.AbsPresenter
import io.github.andremion.jobster.domain.JobRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class JobDetailsPresenter(
    private val jobRepository: JobRepository
) : AbsPresenter<JobDetailsUiState, JobDetailsUiEvent, JobDetailsUiEffect>(
    JobDetailsUiState.Initial
) {

    override fun onUiEvent(uiEvent: JobDetailsUiEvent) {
        when (uiEvent) {
            is JobDetailsUiEvent.Init -> {
                updateUiState { uiState ->
                    uiState.copy(
                        isLoading = true,
                    )
                }
                jobRepository.getJobById(uiEvent.jobId)
                    .onEach { job ->
                        updateUiState { uiState ->
                            uiState.copy(
                                isLoading = false,
                                job = job,
                            )
                        }
                    }
                    .launchIn(presenterScope)
            }

            is JobDetailsUiEvent.ContentClick -> {
                uiEffect.tryEmit(JobDetailsUiEffect.NavigateToUrl(uiEvent.url))
            }
        }
    }

}
