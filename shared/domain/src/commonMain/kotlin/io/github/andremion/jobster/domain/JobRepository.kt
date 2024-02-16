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

package io.github.andremion.jobster.domain

import io.github.andremion.jobster.domain.entity.Job
import io.github.andremion.jobster.domain.entity.JobPosting
import io.github.andremion.jobster.domain.entity.SearchResult
import kotlinx.coroutines.flow.Flow

interface JobRepository {
    fun getJobs(): Flow<List<Job>>
    fun getJobsWithContents(): Flow<List<Job>>
    fun getJobById(id: String): Flow<Job?>
    fun getContentsByIds(ids: List<String>): Flow<List<Job.Content>>
    fun getContents(): Flow<List<Job.Content>>
    suspend fun searchJobPosting(url: String): JobPosting
    suspend fun save(jobPosting: JobPosting, contents: List<JobPosting.Content>)
    suspend fun delete(jobId: String, contentId: String)
    suspend fun delete(contentId: String)
    fun searchForContent(query: String): Flow<List<SearchResult>?>
}
