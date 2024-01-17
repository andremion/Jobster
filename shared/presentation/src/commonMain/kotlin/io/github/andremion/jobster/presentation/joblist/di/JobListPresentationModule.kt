package io.github.andremion.jobster.presentation.joblist.di

import io.github.andremion.jobster.presentation.joblist.JobListViewModel
import org.koin.dsl.module

internal object JobListPresentationModule {
    val module = module {
        factory {
            JobListViewModel(
                jobRepository = get()
            )
        }
    }
}
