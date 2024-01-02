package io.github.andremion.interprep.presentation.joblist

sealed interface JobListUiEvent {
    data object Init : JobListUiEvent
    data class JobClick(val jobId: String) : JobListUiEvent
}
