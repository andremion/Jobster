/*
 *    Copyright 2024. André Luiz Oliveira Rêgo
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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
