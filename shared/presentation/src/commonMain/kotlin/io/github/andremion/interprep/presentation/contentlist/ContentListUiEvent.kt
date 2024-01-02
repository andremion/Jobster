package io.github.andremion.interprep.presentation.contentlist

sealed interface ContentListUiEvent {
    data object Init : ContentListUiEvent
    data class ContentClick(val url: String) : ContentListUiEvent
}
