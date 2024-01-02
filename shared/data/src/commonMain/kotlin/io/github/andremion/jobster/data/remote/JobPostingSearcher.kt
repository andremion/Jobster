package io.github.andremion.jobster.data.remote

import io.github.andremion.jobster.data.remote.api.GeminiApi
import io.github.andremion.jobster.data.remote.model.GetJobPostingResponse

internal class JobPostingSearcher(
    private val webScrapper: WebScrapper,
    private val geminiApi: GeminiApi,
) {

    suspend fun search(url: String): GetJobPostingResponse =
        webScrapper.scrap(url).let { document ->
            geminiApi.getJobPosting(document.content)
        }
}
