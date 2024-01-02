package io.github.andremion.interprep.domain.entity

data class SearchResult(
    val type: Type,
    val id: String,
    val title: String,
    val description: String,
    val url: String,
) {
    enum class Type {
        Job,
        Content
    }
}
