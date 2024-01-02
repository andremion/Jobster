package io.github.andremion.interprep.presentation.joblist.di

import io.github.andremion.interprep.presentation.joblist.JobListPresenter
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
