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

package io.github.andremion.jobster.data

import io.github.andremion.jobster.data.local.db.ContentTable
import io.github.andremion.jobster.data.local.db.GetJobById
import io.github.andremion.jobster.data.local.db.JobDao
import io.github.andremion.jobster.data.local.db.JobTable
import io.github.andremion.jobster.data.local.db.SearchForContent
import io.github.andremion.jobster.data.local.db.SelectAllWithContents
import io.github.andremion.jobster.data.local.db.mapper.transform
import io.github.andremion.jobster.data.remote.JobPostingSearcher
import io.github.andremion.jobster.data.remote.mapper.transform
import io.github.andremion.jobster.domain.JobRepository
import io.github.andremion.jobster.domain.entity.Job
import io.github.andremion.jobster.domain.entity.JobPosting
import io.github.andremion.jobster.domain.entity.SearchResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class JobRepositoryImpl(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val jobPostingSearcher: JobPostingSearcher,
    private val jobDao: JobDao,
) : JobRepository {

    override fun getJobs(): Flow<List<Job>> =
        jobDao.selectAll()
            .map(List<JobTable>::transform)

    override fun getJobsWithContents(): Flow<List<Job>> =
        jobDao.selectAllWithContents()
            .map(List<SelectAllWithContents>::transform)

    override fun getJobById(id: String): Flow<Job?> =
        jobDao.getJobById(id)
            .map(List<GetJobById>::transform)

    override fun getContentsByIds(ids: List<String>): Flow<List<Job.Content>> =
        jobDao.getContentsByIds(ids)
            .map(List<ContentTable>::transform)

    override fun getContents(): Flow<List<Job.Content>> =
        jobDao.getContents()
            .map(List<ContentTable>::transform)

    override suspend fun searchJobPosting(url: String): JobPosting = withContext(dispatcher) {
        jobPostingSearcher.search(url)
            .transform(url)
    }

    override suspend fun save(jobPosting: JobPosting, contents: List<JobPosting.Content>) = withContext(dispatcher) {
        jobDao.insert(jobPosting, contents)
    }

    override suspend fun delete(jobId: String, contentId: String) = withContext(dispatcher) {
        jobDao.delete(jobId, contentId)
    }

    override suspend fun delete(contentId: String) = withContext(dispatcher) {
        jobDao.delete(contentId)
    }

    override fun searchForContent(query: String): Flow<List<SearchResult>?> =
        if (query.isNotBlank() && query.length >= MinimumQueryLength) {
            jobDao.searchForContent(query)
                .map(List<SearchForContent>::transform)
        } else {
            flowOf(null)
        }
}

private const val MinimumQueryLength = 2
