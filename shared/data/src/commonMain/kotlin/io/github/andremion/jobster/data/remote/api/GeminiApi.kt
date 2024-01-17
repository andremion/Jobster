package io.github.andremion.jobster.data.remote.api

import io.github.andremion.jobster.data.remote.model.GetJobPostingResponse
import io.github.andremion.jobster.domain.exception.JobPostingSearchException
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

// Not internal because it is implemented in Swift too
abstract class GeminiApi {

    private val jsonParser = Json.Default

    abstract suspend fun getJobPosting(content: String): GetJobPostingResponse

    @Throws(
        JobPostingSearchException.Server::class,
        JobPostingSearchException.General::class,
    )
    protected fun getResponse(
        content: String,
        getResponseString: suspend (prompt: String) -> String
    ): GetJobPostingResponse = runBlocking {
        val prompt = getPrompt(content)
        println("Asking to Gemini...\n\n$prompt")
        val responseString = getResponseString(prompt)
        println("Gemini response: $responseString")
        decodeResponseString(responseString)
    }

    private fun getPrompt(content: String): String =
        "I want to prepare myself for an interview but I don't know where to find relevant topics for a job posting on the internet.\n\n" +
            "Given this job posting:\n\n$content\n\n" +
            "Give me the role, company name, company logo, and the relevant topic links for the role in a JSON format, " +
            "where the root object contains these non-nullable attributes: " +
            "\"role\" as String, \"company\" as String, \"logo\" as String, and \"contents\" as an array of objects " +
            "containing these non-nullable attributes: " +
            "\"title\" as String, \"description\" as String, \"url\" as String, and \"image\" as String"

    private fun decodeResponseString(responseString: String): GetJobPostingResponse =
        jsonParser.decodeFromString<GetJobPostingResponse>(
            responseString
                .replaceBefore(delimiter = "{", replacement = "")
                .replaceAfterLast(delimiter = "}", replacement = "")
        )
}
