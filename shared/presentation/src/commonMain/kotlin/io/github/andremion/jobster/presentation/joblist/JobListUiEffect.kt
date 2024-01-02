package io.github.andremion.jobster.presentation.joblist

sealed interface JobListUiEffect {
    data class NavigateToJobDetails(val jobId: String) : JobListUiEffect
}
