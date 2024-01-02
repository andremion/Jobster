package io.github.andremion.interprep.domain

import io.github.andremion.interprep.domain.entity.Job
import io.github.andremion.interprep.domain.entity.JobPosting
import io.github.andremion.interprep.domain.entity.SearchResult
import kotlinx.coroutines.flow.Flow

interface JobRepository {
    data class GeneralJobPostingSearchException(
        override val cause: Throwable?
    ) : Exception()

    data class JobPostingSearchException(
        override val message: String,
        override val cause: Throwable?
    ) : Exception(message, cause)

    fun getJobs(): Flow<List<Job>>
    fun getJobsWithContents(): Flow<List<Job>>
    fun getJobById(id: String): Flow<Job?>
    fun getContentsByIds(ids: List<String>): Flow<List<Job.Content>>
    fun getContents(): Flow<List<Job.Content>>
    suspend fun searchJobPosting(url: String): JobPosting
    suspend fun save(jobPosting: JobPosting, contents: List<JobPosting.Content>)
    suspend fun delete(jobId: String, contentId: String)
    fun searchForContent(query: String): Flow<List<SearchResult>?>
}
