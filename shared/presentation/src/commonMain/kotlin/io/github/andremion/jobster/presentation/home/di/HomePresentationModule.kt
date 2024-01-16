package io.github.andremion.jobster.presentation.home.di

import io.github.andremion.jobster.presentation.home.HomeViewModel
import org.koin.dsl.module

internal object HomePresentationModule {
    val module = module {
        factory {
            HomeViewModel(
                jobRepository = get()
            )
        }
    }
}
