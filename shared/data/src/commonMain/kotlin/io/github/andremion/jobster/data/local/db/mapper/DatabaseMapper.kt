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

package io.github.andremion.jobster.data.local.db.mapper

import io.github.andremion.jobster.data.local.db.ContentTable
import io.github.andremion.jobster.data.local.db.GetJobById
import io.github.andremion.jobster.data.local.db.JobTable
import io.github.andremion.jobster.data.local.db.SearchForContent
import io.github.andremion.jobster.data.local.db.SelectAllWithContents
import io.github.andremion.jobster.domain.entity.Job
import io.github.andremion.jobster.domain.entity.SearchResult
import kotlin.jvm.JvmName

internal fun List<JobTable>.transform(): List<Job> =
    map(JobTable::transform)

@JvmName("transformContents")
internal fun List<ContentTable>.transform(): List<Job.Content> =
    map(ContentTable::transform)

private fun JobTable.transform(): Job =
    Job(
        id = jobId,
        title = title,
        company = company,
        url = url,
        logo = logo,
        contents = emptyList()
    )

private fun ContentTable.transform(): Job.Content =
    Job.Content(
        id = contentId,
        title = title,
        description = description,
        url = url,
        image = image
    )

internal fun List<GetJobById>.transform(): Job? =
    groupBy { jobById ->
        Job(
            id = jobById.jobId,
            title = jobById.title,
            company = jobById.company,
            url = jobById.url,
            logo = jobById.logo,
            contents = emptyList()
        )
    }.map { (job, jobByIds) ->
        job.copy(
            contents = jobByIds.map { jobById ->
                Job.Content(
                    id = jobById.contentId,
                    title = jobById.title_,
                    description = jobById.description,
                    url = jobById.url_,
                    image = jobById.image
                )
            }
        )
    }.firstOrNull()

@JvmName("transformJobWithContents")
internal fun List<SelectAllWithContents>.transform(): List<Job> =
    groupBy { jobWithContents ->
        Job(
            id = jobWithContents.jobId,
            title = jobWithContents.title,
            company = jobWithContents.company,
            url = jobWithContents.url,
            logo = jobWithContents.logo,
            contents = emptyList()
        )
    }.map { (job, jobsWithContents) ->
        job.copy(
            contents = jobsWithContents.map { jobWithContents ->
                Job.Content(
                    id = jobWithContents.contentId,
                    title = jobWithContents.title_,
                    description = jobWithContents.description,
                    url = jobWithContents.url,
                    image = jobWithContents.image
                )
            }
        )
    }

@JvmName("transformSearchContent")
internal fun List<SearchForContent>.transform(): List<SearchResult> =
    map(SearchForContent::transform)

private fun SearchForContent.transform(): SearchResult =
    SearchResult(
        type = SearchResult.Type.valueOf(type),
        id = id,
        title = title,
        description = description,
        url = url
    )
