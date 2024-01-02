package io.github.andremion.jobster.presentation.jobdetails

import io.github.andremion.jobster.domain.entity.Job

data class JobDetailsUiState(
    val isLoading: Boolean,
    val job: Job?,
) {
    companion object {
        val Initial = JobDetailsUiState(
            isLoading = false,
            job = null,
        )
    }
}
