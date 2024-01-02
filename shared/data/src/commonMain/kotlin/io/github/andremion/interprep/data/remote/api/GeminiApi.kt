package io.github.andremion.interprep.data.remote.api

import io.github.andremion.interprep.data.remote.model.GetJobPostingResponse

internal interface GeminiApi {
    suspend fun getJobPosting(content: String): GetJobPostingResponse
}
