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
                jobContentQueries.delete(jobId, contentId)
                if (jobContentQueries.countByContentId(contentId).executeAsOne() == 0L) {
                    jobQueries.deleteById(jobId)
                    contentQueries.deleteById(contentId)
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
