package io.github.andremion.jobster.presentation.jobdetails

sealed interface JobDetailsUiEvent {
    data class ContentClick(val url: String) : JobDetailsUiEvent
}
