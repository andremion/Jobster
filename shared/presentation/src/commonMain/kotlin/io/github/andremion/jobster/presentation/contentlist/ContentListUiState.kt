package io.github.andremion.jobster.presentation.contentlist

import io.github.andremion.jobster.domain.entity.Job

data class ContentListUiState(
    val isLoading: Boolean = false,
    val contents: List<Job.Content>? = null,
)
