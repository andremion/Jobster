package io.github.andremion.interprep.presentation.joblist

sealed interface JobListUiEffect {
    data class NavigateToJobDetails(val jobId: String) : JobListUiEffect
}
