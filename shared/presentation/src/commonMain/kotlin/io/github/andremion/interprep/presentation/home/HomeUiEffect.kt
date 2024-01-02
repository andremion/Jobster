package io.github.andremion.interprep.presentation.home

sealed interface HomeUiEffect {
    data object NavigateToJobPostingSearch : HomeUiEffect
    data class NavigateToJobDetails(val jobId: String) : HomeUiEffect
    data class NavigateToUrl(val url: String) : HomeUiEffect
}
