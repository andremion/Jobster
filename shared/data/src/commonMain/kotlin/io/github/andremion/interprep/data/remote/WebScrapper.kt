package io.github.andremion.interprep.data.remote

import io.github.andremion.interprep.data.remote.model.ScrappedDocument

internal interface WebScrapper {
    suspend fun scrap(url: String): ScrappedDocument
}
