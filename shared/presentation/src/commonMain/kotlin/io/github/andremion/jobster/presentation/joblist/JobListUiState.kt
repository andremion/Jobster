package io.github.andremion.jobster.presentation.joblist

data class JobListUiState(
    val isLoading: Boolean = false,
    val jobs: List<Job>? = null,
    val error: Throwable? = null
) {
    data class Job(
        val id: String,
        val title: String,
        val company: String,
        val url: String,
        val logo: String?,
        val content: String?,
    )
}
