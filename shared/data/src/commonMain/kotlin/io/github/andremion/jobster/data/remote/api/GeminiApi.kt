package io.github.andremion.jobster.data.remote.api

import io.github.andremion.jobster.data.remote.model.GetJobPostingResponse

internal interface GeminiApi {
    suspend fun getJobPosting(content: String): GetJobPostingResponse
}
