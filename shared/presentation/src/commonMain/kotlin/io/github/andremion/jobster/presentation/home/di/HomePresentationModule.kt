package io.github.andremion.jobster.presentation.home.di

import io.github.andremion.jobster.presentation.home.HomePresenter
import org.koin.dsl.module

internal object HomePresentationModule {
    val module = module {
        factory {
            HomePresenter(
                jobRepository = get()
            )
        }
    }
}
