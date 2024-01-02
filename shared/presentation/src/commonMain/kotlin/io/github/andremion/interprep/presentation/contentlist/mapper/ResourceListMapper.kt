package io.github.andremion.interprep.presentation.contentlist.mapper

import io.github.andremion.interprep.domain.entity.Job
import io.github.andremion.interprep.presentation.contentlist.ContentListUiState

internal fun List<Job.Content>.transform(): List<ContentListUiState.Content> =
    map { content ->
        ContentListUiState.Content(
            id = content.id,
            title = content.title,
            description = content.description,
            url = content.url,
            image = content.image,
        )
    }
