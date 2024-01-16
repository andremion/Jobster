package io.github.andremion.jobster.presentation.jobdetails

import io.github.andremion.jobster.domain.entity.Job

data class JobDetailsUiState(
    val isLoading: Boolean = false,
    val job: Job? = null,
)
