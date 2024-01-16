package io.github.andremion.jobster.presentation.jobdetails.di

import io.github.andremion.jobster.presentation.jobdetails.JobDetailsViewModel
import org.koin.dsl.module

internal object JobDetailsPresentationModule {
    val module = module {
        factory { parameters ->
            JobDetailsViewModel(
                jobId = parameters.get(),
                jobRepository = get(),
            )
        }
    }
}
