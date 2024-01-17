package io.github.andremion.jobster.presentation.joblist

sealed interface JobListUiEvent {
    data class JobClick(val jobId: String) : JobListUiEvent
}
