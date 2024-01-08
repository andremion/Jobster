package io.github.andremion.jobster.data.remote.api

import io.github.andremion.jobster.data.remote.model.GetJobPostingResponse
import kotlinx.coroutines.delay

internal class GeminiApiImpl : GeminiApi {

    override suspend fun getJobPosting(content: String): GetJobPostingResponse =
        delay(2000).run {
            GetJobPostingResponse(
                role = "Role",
                company = "Company",
                logo = null,
                contents = listOf(
                    GetJobPostingResponse.Content(
                        title = "Title",
                        description = "Description",
                        url = "https://www.google.com",
                        image = null,
                    )
                ),
            )
        }
}
