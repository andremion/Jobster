package io.github.andremion.jobster.presentation.contentlist

data class ContentListUiState(
    val isLoading: Boolean,
    val contents: List<Content>?,
    val error: Throwable?
) {
    companion object {
        val Initial = ContentListUiState(
            isLoading = false,
            contents = null,
            error = null
        )
    }

    data class Content(
        val id: String,
        val title: String,
        val description: String,
        val url: String,
        val image: String?,
    )
}
