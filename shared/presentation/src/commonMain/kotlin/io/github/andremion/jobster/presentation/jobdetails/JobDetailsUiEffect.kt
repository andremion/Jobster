package io.github.andremion.jobster.presentation.jobdetails

sealed interface JobDetailsUiEffect {
    data class NavigateToUrl(val url: String) : JobDetailsUiEffect
}
