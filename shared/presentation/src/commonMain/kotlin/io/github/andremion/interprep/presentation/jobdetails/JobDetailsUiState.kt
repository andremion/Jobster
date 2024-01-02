package io.github.andremion.interprep.presentation.jobdetails

import io.github.andremion.interprep.domain.entity.Job

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
