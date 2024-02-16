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

package io.github.andremion.jobster.data.local.db

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import io.github.andremion.jobster.domain.entity.JobPosting
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

internal class JobDao(
    private val dispatcher: CoroutineDispatcher,
    private val jobQueries: JobQueries,
    private val contentQueries: ContentQueries,
    private val jobContentQueries: JobContentQueries,
) {

    fun selectAll(): Flow<List<JobTable>> =
        jobQueries.selectAll().asFlow()
            .mapToList(dispatcher)

    fun selectAllWithContents(): Flow<List<SelectAllWithContents>> =
        jobQueries.selectAllWithContents().asFlow()
            .mapToList(dispatcher)

    fun getJobById(id: String): Flow<List<GetJobById>> =
        jobQueries.getJobById(id).asFlow()
            .mapToList(dispatcher)

    fun getContentsByIds(ids: List<String>): Flow<List<ContentTable>> =
        contentQueries.getByIds(ids).asFlow()
            .mapToList(dispatcher)

    fun getContents(): Flow<List<ContentTable>> =
        contentQueries.selectAll().asFlow()
            .mapToList(dispatcher)

    suspend fun delete(jobId: String, contentId: String) {
        withContext(dispatcher) {
            jobQueries.transaction {
                jobContentQueries.deleteByJobIdAndContentId(jobId, contentId)
                contentQueries.deleteById(contentId)
                if (jobContentQueries.countByJobId(jobId).executeAsOne() == 0L) {
                    jobQueries.deleteById(jobId)
                }
            }
        }
    }

    suspend fun delete(contentId: String) {
        withContext(dispatcher) {
            jobQueries.transaction {
                val jobIds = jobContentQueries.getJobIdsByContentId(contentId).executeAsList()
                jobContentQueries.deleteByContentId(contentId)
                contentQueries.deleteById(contentId)
                jobIds.forEach { jobId ->
                    if (jobContentQueries.countByJobId(jobId).executeAsOne() == 0L) {
                        jobQueries.deleteById(jobId)
                    }
                }
            }
        }
    }

    suspend fun insert(jobPosting: JobPosting, contents: List<JobPosting.Content>) {
        withContext(dispatcher) {
            jobQueries.transaction {
                val jobId = jobPosting.url
                jobQueries.upsert(
                    jobId = jobId,
                    title = jobPosting.role,
                    company = jobPosting.company,
                    url = jobPosting.url,
                    logo = jobPosting.logo
                )
                contents.forEach { content ->
                    val contentId = content.url
                    contentQueries.upsert(
                        contentId = contentId,
                        title = content.title,
                        description = content.description,
                        url = content.url,
                        image = content.image
                    )
                    jobContentQueries.upsert(
                        jobId = jobId,
                        contentId = contentId
                    )
                }
            }
        }
    }

    fun searchForContent(query: String): Flow<List<SearchForContent>> =
        jobQueries.searchForContent("%$query%").asFlow()
            .mapToList(dispatcher)
}
