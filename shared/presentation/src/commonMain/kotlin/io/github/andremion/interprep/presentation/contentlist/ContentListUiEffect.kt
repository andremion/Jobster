package io.github.andremion.interprep.presentation.contentlist

sealed interface ContentListUiEffect {
    data class NavigateToUrl(val url: String) : ContentListUiEffect
}
