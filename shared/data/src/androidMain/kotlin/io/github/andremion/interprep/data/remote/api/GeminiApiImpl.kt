package io.github.andremion.interprep.data.remote.api

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.ServerException
import io.github.andremion.interprep.data.remote.model.GetJobPostingResponse
import io.github.andremion.interprep.domain.JobRepository

internal class GeminiApiImpl : GeminiApi {

    private val generativeModel = GenerativeModel(
        // Use a model that's applicable for your use case (see "Implement basic use cases" below)
        modelName = "gemini-pro",
        // Access your API key as a Build Configuration variable (see "Set up your API key" above)
        apiKey = "AIzaSyBbk-DVoHgJdcwlY1HHTr-gjwOBIWl_YLo"
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
    "I want to prepare myself for an interview.\n" +
        "Given this content of a job posting:\n\n$content\n\n" +
        "Give me the role, company name, company logo, and the relevant contents for the role in a JSON format, " +
        "where the root object contains these attributes: \"role\" as String, \"company\" as String, \"logo\" as String, " +
        "and \"contents\" as an array of objects " +
        "with \"title\" as String, \"description\" as String, \"url\" as String, and \"image\" as String, " +
        "so I can prepare myself for an interview for it.\n\n" +
        "IMPORTANT NOTE: ALL THE JSON ATTRIBUTES SHOULD HAVE RELEVANT CONTENT AND NOT BE NULL!!!"
