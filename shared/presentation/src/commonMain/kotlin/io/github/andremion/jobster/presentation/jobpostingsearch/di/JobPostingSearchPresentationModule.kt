package io.github.andremion.jobster.presentation.jobpostingsearch.di

import io.github.andremion.jobster.presentation.jobpostingsearch.JobPostingSearchViewModel
import org.koin.dsl.module

internal object JobPostingSearchPresentationModule {
    val module = module {
        factory {
            JobPostingSearchViewModel(
                jobRepository = get()
            )
        }
    }
}
