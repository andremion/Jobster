package io.github.andremion.jobster.data.remote

import io.github.andremion.jobster.data.remote.model.ScrappedDocument

internal interface WebScrapper {
    suspend fun scrap(url: String): ScrappedDocument
}
