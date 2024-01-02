package io.github.andremion.interprep.domain.entity

data class Job(
    val id: String,
    val title: String,
    val company: String,
    val url: String,
    val logo: String?,
    val contents: List<Content>,
) {
    data class Content(
        val id: String,
        val title: String,
        val description: String,
        val url: String,
        val image: String?,
    )
}
