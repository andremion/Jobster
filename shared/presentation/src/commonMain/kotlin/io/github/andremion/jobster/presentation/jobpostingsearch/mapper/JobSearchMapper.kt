package io.github.andremion.jobster.presentation.jobpostingsearch.mapper

import io.github.andremion.jobster.domain.entity.JobPosting
import io.github.andremion.jobster.presentation.jobpostingsearch.JobPostingSearchUiState

internal fun JobPosting.transform(existingContentIds: List<String>): JobPostingSearchUiState.JobPosting =
    JobPostingSearchUiState.JobPosting(
        role = role,
        company = company,
        logo = logo,
        contents = contents.map { content ->
            JobPostingSearchUiState.JobPosting.Content(
                title = content.title,
                description = content.description,
                url = content.url,
                image = content.image,
                isChecked = existingContentIds.contains(content.url),
            )
        },
    )

internal fun JobPostingSearchUiState.JobPosting.transform(url: String): JobPosting =
    JobPosting(
        url = url,
        role = role,
        company = company,
        logo = logo,
        contents = contents.map { content ->
            JobPosting.Content(
                title = content.title,
                description = content.description,
                url = content.url,
                image = content.image,
            )
        },
    )

internal fun JobPostingSearchUiState.JobPosting.Content.transform(): JobPosting.Content =
    JobPosting.Content(
        title = title,
        description = description,
        url = url,
        image = image,
    )
