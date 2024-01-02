package io.github.andremion.interprep.data

import io.github.andremion.interprep.data.local.db.ContentTable
import io.github.andremion.interprep.data.local.db.GetJobById
import io.github.andremion.interprep.data.local.db.JobDao
import io.github.andremion.interprep.data.local.db.JobTable
import io.github.andremion.interprep.data.local.db.SearchForContent
import io.github.andremion.interprep.data.local.db.SelectAllWithContents
import io.github.andremion.interprep.data.local.db.mapper.transform
import io.github.andremion.interprep.data.remote.JobPostingSearcher
import io.github.andremion.interprep.data.remote.mapper.transform
import io.github.andremion.interprep.domain.JobRepository
import io.github.andremion.interprep.domain.entity.Job
import io.github.andremion.interprep.domain.entity.JobPosting
import io.github.andremion.interprep.domain.entity.SearchResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

internal class JobRepositoryImpl(
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

    override suspend fun searchJobPosting(url: String): JobPosting =
        jobPostingSearcher.search(url)
            .transform(url)

    override suspend fun save(jobPosting: JobPosting, contents: List<JobPosting.Content>) {
        jobDao.insert(jobPosting, contents)
    }

    override suspend fun delete(jobId: String, contentId: String) {
        jobDao.delete(jobId, contentId)
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
