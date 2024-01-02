package io.github.andremion.interprep.presentation.joblist.mapper

import io.github.andremion.interprep.domain.entity.Job
import io.github.andremion.interprep.presentation.joblist.JobListUiState

internal fun List<Job>.transform(): List<JobListUiState.Job> =
    map { job ->
        JobListUiState.Job(
            id = job.id,
            title = job.title,
            company = job.company,
            url = job.url,
            logo = job.logo,
            contents = job.contents.joinToString(transform = Job.Content::title)
        )
    }
