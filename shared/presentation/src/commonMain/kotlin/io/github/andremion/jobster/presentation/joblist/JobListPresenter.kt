package io.github.andremion.jobster.presentation.joblist

import io.github.andremion.boomerang.AbsPresenter
import io.github.andremion.jobster.domain.JobRepository
import io.github.andremion.jobster.presentation.joblist.mapper.transform
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class JobListPresenter(
    private val jobRepository: JobRepository
) : AbsPresenter<JobListUiState, JobListUiEvent, JobListUiEffect>(JobListUiState.Initial) {

    override fun onUiEvent(uiEvent: JobListUiEvent) {
        when (uiEvent) {
            JobListUiEvent.Init -> {
                updateUiState { uiState ->
                    uiState.copy(
                        isLoading = true,
                    )
                }
                jobRepository.getJobsWithContents()
                    .onEach { jobs ->
                        updateUiState { uiState ->
                            uiState.copy(
                                isLoading = false,
                                jobs = jobs.transform(),
                                error = null
                            )
                        }
                    }
                    .launchIn(presenterScope)
            }

            is JobListUiEvent.JobClick -> {
                uiEffect.tryEmit(JobListUiEffect.NavigateToJobDetails(uiEvent.jobId))
            }
        }
    }
}
