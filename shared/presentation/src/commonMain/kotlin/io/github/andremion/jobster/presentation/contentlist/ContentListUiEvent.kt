package io.github.andremion.jobster.presentation.contentlist

sealed interface ContentListUiEvent {
    data class ContentClick(val url: String) : ContentListUiEvent
}
