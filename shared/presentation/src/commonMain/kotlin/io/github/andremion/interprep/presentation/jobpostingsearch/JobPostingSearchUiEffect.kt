package io.github.andremion.interprep.presentation.jobpostingsearch

sealed interface JobPostingSearchUiEffect {
    data class NavigateToUrl(val url: String) : JobPostingSearchUiEffect
}
