package io.github.andremion.interprep.data.remote.mapper

import io.github.andremion.interprep.data.remote.model.GetJobPostingResponse
import io.github.andremion.interprep.domain.entity.JobPosting

internal fun GetJobPostingResponse.transform(url: String) = JobPosting(
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
    }
)