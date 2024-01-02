package io.github.andremion.interprep.presentation.jobdetails.di

import io.github.andremion.interprep.presentation.jobdetails.JobDetailsPresenter
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
