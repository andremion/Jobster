package io.github.andremion.jobster.presentation.joblist.di

import io.github.andremion.jobster.presentation.joblist.JobListPresenter
import org.koin.dsl.module

internal object JobListPresentationModule {
    val module = module {
        factory {
            JobListPresenter(
                jobRepository = get()
            )
        }
    }
}
