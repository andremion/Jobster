package io.github.andremion.jobster.presentation.contentlist

sealed interface ContentListUiEffect {
    data class NavigateToUrl(val url: String) : ContentListUiEffect
}
