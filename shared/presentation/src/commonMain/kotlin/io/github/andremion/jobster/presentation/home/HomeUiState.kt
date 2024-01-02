package io.github.andremion.jobster.presentation.home

import io.github.andremion.jobster.domain.entity.SearchResult

data class HomeUiState(
    val query: String,
    val isSearchBarActive: Boolean,
    val searchResults: List<SearchResult>?,
) {
    companion object {
        val Initial = HomeUiState(
            query = "",
            isSearchBarActive = false,
            searchResults = null
        )
    }
}
