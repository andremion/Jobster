package io.github.andremion.jobster.presentation.jobdetails.di

import io.github.andremion.jobster.presentation.jobdetails.JobDetailsPresenter
import org.koin.dsl.module

internal object JobDetailsPresentationModule {
    val module = module {
        factory {
            JobDetailsPresenter(
                jobRepository = get()
            )
        }
    }
}
