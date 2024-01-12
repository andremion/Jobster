package io.github.andremion.jobster.presentation.jobpostingsearch

import io.github.andremion.jobster.domain.exception.JobPostingSearchException

data class JobPostingSearchUiState(
    val url: String,
    val isSearchBarActive: Boolean,
    val isLoading: Boolean,
    val jobPosting: JobPosting?,
    val error: JobPostingSearchException?,
) {
    companion object {
        val Initial = JobPostingSearchUiState(
            url = "",
            isSearchBarActive = false,
            isLoading = false,
            jobPosting = null,
            error = null,
        )
    }

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
