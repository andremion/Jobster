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

package io.github.andremion.jobster.presentation.joblist.mapper

import io.github.andremion.jobster.domain.entity.Job
import io.github.andremion.jobster.presentation.joblist.JobListUiState

internal fun List<Job>.transform(): List<JobListUiState.Job> =
    map { job ->
        JobListUiState.Job(
            id = job.id,
            title = job.title,
            company = job.company,
            url = job.url,
            logo = job.logo,
            content = job.contents.joinToString(transform = Job.Content::title)
        )
    }
