package io.github.andremion.jobster.presentation.contentlist

import io.github.andremion.jobster.domain.entity.Job

data class ContentListUiState(
    val isLoading: Boolean,
    val contents: List<Job.Content>?,
    val error: Throwable?
) {
    companion object {
        val Initial = ContentListUiState(
            isLoading = false,
            contents = null,
            error = null
        )
    }
}
