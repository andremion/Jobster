package io.github.andremion.jobster.data.remote

import io.github.andremion.jobster.data.remote.model.ScrappedDocument
import kotlinx.coroutines.delay

internal class WebScrapperImpl : WebScrapper {

    override suspend fun scrap(url: String): ScrappedDocument =
        delay(2000).run {
            ScrappedDocument(
                title = "WebScrapperImpl",
                images = emptyList(),
                content = "",
            )
        }
}
