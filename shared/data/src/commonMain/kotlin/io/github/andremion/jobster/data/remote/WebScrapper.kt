package io.github.andremion.jobster.data.remote

import com.fleeksoft.ksoup.Ksoup
import io.github.andremion.jobster.domain.exception.JobPostingSearchException
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

internal class WebScrapper(
    private val client: HttpClient
) {

    suspend fun scrap(url: String): String =
        try {
            println("Scrapping $url...")
            client.get(url)
                .bodyAsText()
                .let(Ksoup::parse)
                .text()
        } catch (cause: Throwable) {
            throw JobPostingSearchException.General(cause)
        }
}
