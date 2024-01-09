package io.github.andremion.jobster.data.remote.api

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.ServerException
import io.github.andremion.jobster.data.BuildConfig
import io.github.andremion.jobster.data.remote.model.GetJobPostingResponse
import io.github.andremion.jobster.domain.JobRepository

internal class GeminiApiImpl : GeminiApi {

    private val generativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = BuildConfig.GeminiApiKey
    )

    override suspend fun getJobPosting(content: String): GetJobPostingResponse {
        try {
            val prompt = getPrompt(content)
            println("Asking to Gemini...\n\n$prompt")
            val response = generativeModel.generateContent(prompt)
            val responseString = response.text.toString()
            println("Gemini response: $responseString")
            return JSON.decodeFromString<GetJobPostingResponse>(
                responseString
                    .replaceBefore(delimiter = "{", replacement = "")
                    .replaceAfterLast(delimiter = "}", replacement = "")
            )
        } catch (cause: Throwable) {
            throw when (cause) {
                is ServerException -> JobRepository.JobPostingSearchException(cause.message!!, cause)
                else -> cause
            }
        }
    }
}

private fun getPrompt(content: String): String =
    "I want to prepare myself for an interview but I don't know where to find relevant topics for a job posting on the internet.\n\n" +
    "Given this job posting:\n\n$content\n\n" +
        "Give me the role, company name, company logo, and the relevant topic links for the role in a JSON format, " +
        "where the root object contains these non-nullable attributes: \"role\" as String, \"company\" as String, \"logo\" as String, " +
        "and \"contents\" as an array of objects " +
        "containing these non-nullable attributes: \"title\" as String, \"description\" as String, \"url\" as String, and \"image\" as String"
//        "so I can prepare myself for an interview for it.\n\n"
//        "IMPORTANT NOTE: ALL THE JSON ATTRIBUTES SHOULD HAVE RELEVANT CONTENT AND NOT BE NULL!!!"
