package io.github.andremion.interprep.presentation.jobdetails

sealed interface JobDetailsUiEffect {
    data class NavigateToUrl(val url: String) : JobDetailsUiEffect
}
