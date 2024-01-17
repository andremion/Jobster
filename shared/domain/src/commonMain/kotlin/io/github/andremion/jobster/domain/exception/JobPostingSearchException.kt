package io.github.andremion.jobster.domain.exception

sealed class JobPostingSearchException : Exception() {
    data class General(
        override val cause: Throwable?
    ) : JobPostingSearchException()

    data class Server(
        override val message: String,
        override val cause: Throwable?
    ) : JobPostingSearchException()
}
