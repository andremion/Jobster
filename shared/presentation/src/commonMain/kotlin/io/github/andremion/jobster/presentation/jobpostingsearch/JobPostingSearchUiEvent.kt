package io.github.andremion.jobster.presentation.jobpostingsearch

sealed interface JobPostingSearchUiEvent {
    data object Init : JobPostingSearchUiEvent
    data class UpdateUrl(val url: String) : JobPostingSearchUiEvent
    data class UpdateSearchBarActive(val isActive: Boolean) : JobPostingSearchUiEvent
    data object SearchBarBackClick : JobPostingSearchUiEvent
    data object ClearSearchClick : JobPostingSearchUiEvent
    data object SearchClick : JobPostingSearchUiEvent
    data class ContentTitleClick(val url: String) : JobPostingSearchUiEvent
    data class ContentSwitchClick(
        val content: JobPostingSearchUiState.JobPosting.Content,
        val isChecked: Boolean
    ) : JobPostingSearchUiEvent
}
