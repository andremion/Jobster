package io.github.andremion.interprep.domain.entity

data class JobPosting(
    val url: String,
    val role: String,
    val company: String,
    val logo: String?,
    val contents: List<Content>,
) {
    data class Content(
        val title: String,
        val description: String,
        val url: String,
        val image: String?,
    )
}
