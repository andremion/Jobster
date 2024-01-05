package io.github.andremion.jobster.presentation.jobpostingsearch

sealed interface JobPostingSearchUiEffect {
    data class NavigateToUrl(val url: String) : JobPostingSearchUiEffect
}