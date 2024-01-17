package io.github.andremion.jobster.presentation.jobpostingsearch

import io.github.andremion.jobster.domain.exception.JobPostingSearchException

data class JobPostingSearchUiState(
    val url: String = "",
    val isSearchBarActive: Boolean = false,
    val isLoading: Boolean = false,
    val jobPosting: JobPosting? = null,
    val error: JobPostingSearchException? = null,
) {
    data class JobPosting(
        val role: String,
        val company: String,
        val logo: String?,
        val contents: List<Content>,
    ) {
        data class Content(
            val title: String,
            val description: String,
            val url: String,
            val image: String?,
            val isChecked: Boolean,
        )
    }
}
