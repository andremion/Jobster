package io.github.andremion.jobster.presentation.home

import io.github.andremion.jobster.domain.entity.SearchResult

sealed interface HomeUiEvent {
    data object AddContentClick : HomeUiEvent
    data class UpdateQuery(val query: String) : HomeUiEvent
    data class UpdateSearchBarActive(val isActive: Boolean) : HomeUiEvent
    data object SearchBarBackClick : HomeUiEvent
    data object ClearSearchClick : HomeUiEvent
    data class SearchResultClick(
        val type: SearchResult.Type,
        val id: String,
        val url: String,
    ) : HomeUiEvent
}
