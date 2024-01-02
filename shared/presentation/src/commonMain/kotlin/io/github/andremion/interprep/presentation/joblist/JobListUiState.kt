package io.github.andremion.interprep.presentation.joblist

data class JobListUiState(
    val isLoading: Boolean,
    val jobs: List<Job>?,
    val error: Throwable?
) {
    companion object {
        val Initial = JobListUiState(
            isLoading = false,
            jobs = null,
            error = null
        )
    }

    data class Job(
        val id: String,
        val title: String,
        val company: String,
        val url: String,
        val logo: String?,
        val contents: String?,
    )
}
