package io.github.andremion.interprep.presentation.jobpostingsearch.di

import io.github.andremion.interprep.presentation.jobpostingsearch.JobPostingSearchPresenter
import org.koin.dsl.module

internal object JobPostingSearchPresentationModule {
    val module = module {
        factory {
            JobPostingSearchPresenter(
                jobRepository = get()
            )
        }
    }
}
