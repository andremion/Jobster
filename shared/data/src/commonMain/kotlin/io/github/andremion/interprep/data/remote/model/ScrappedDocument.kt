package io.github.andremion.interprep.data.remote.model

data class ScrappedDocument(
    val title: String,
    val images: List<String>,
    val content: String,
)
