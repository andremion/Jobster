package io.github.andremion.jobster.presentation.home

import io.github.andremion.jobster.domain.entity.SearchResult

data class HomeUiState(
    val isEmptyHintVisible: Boolean = false,
    val query: String = "",
    val isSearchBarActive: Boolean = false,
    val searchResults: List<SearchResult>? = null,
)
