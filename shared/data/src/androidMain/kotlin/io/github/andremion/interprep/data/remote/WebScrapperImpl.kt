package io.github.andremion.interprep.data.remote

import io.github.andremion.interprep.data.remote.model.ScrappedDocument
import io.github.andremion.interprep.domain.JobRepository
import it.skrape.core.htmlDocument
import it.skrape.fetcher.AsyncFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import java.net.SocketTimeoutException

internal class WebScrapperImpl : WebScrapper {

    override suspend fun scrap(url: String): ScrappedDocument =
        try {
            skrape(AsyncFetcher) {
                println("Scrapping $url...")
                request {
                    this.url = url
                    timeout = ScrappingTimeout
                }
                response {
                    htmlDocument {
                        ScrappedDocument(
                            title = titleText,
                            images = eachImage.values.toList(),
                            content = text,
                        )
                    }
                }
            }
        } catch (cause: Throwable) {
            throw when (cause) {
                is SocketTimeoutException -> JobRepository.GeneralJobPostingSearchException(cause)
                else -> cause
            }
        }
}

private const val ScrappingTimeout = 10_000
