package io.github.andremion.jobster.data.remote.api

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.ServerException
import io.github.andremion.jobster.data.BuildConfig
import io.github.andremion.jobster.data.remote.model.GetJobPostingResponse
import io.github.andremion.jobster.domain.exception.JobPostingSearchException

internal class GeminiApiImpl : GeminiApi() {

    private val generativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = BuildConfig.GeminiApiKey
    )

    override suspend fun getJobPosting(content: String): GetJobPostingResponse =
        try {
            getResponse(content) { prompt ->
                generativeModel.generateContent(prompt)
                    .text.toString()
            }
        } catch (cause: ServerException) {
            throw JobPostingSearchException.Server(cause.message!!, cause)
        } catch (cause: Throwable) {
            throw JobPostingSearchException.General(cause)
        }
}
