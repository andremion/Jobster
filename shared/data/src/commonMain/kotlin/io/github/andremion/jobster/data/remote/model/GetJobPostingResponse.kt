package io.github.andremion.jobster.data.remote.model

import kotlinx.serialization.Serializable

// Not internal because it is used in Swift too
@Serializable
data class GetJobPostingResponse(
    val role: String,
    val company: String,
    val logo: String?,
    val contents: List<Content>,
) {

    @Serializable
    data class Content(
        val title: String,
        val description: String,
        val url: String,
        val image: String?,
    )
}
