package io.github.andremion.interprep.presentation.jobpostingsearch

data class JobPostingSearchUiState(
    val url: String,
    val isSearchBarActive: Boolean,
    val isLoading: Boolean,
    val jobPosting: JobPosting?,
    val error: Throwable?,
) {
    companion object {
        val Initial = JobPostingSearchUiState(
            url = "https://jobs.lever.co/jobandtalent/093d4d73-7985-40b9-868d-89ed023a3126",
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
