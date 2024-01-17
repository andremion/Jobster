package io.github.andremion.jobster.di

import io.github.andremion.jobster.data.di.DataModule
import io.github.andremion.jobster.presentation.di.PresentationModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

internal fun initDI(): KoinApplication =
    startKoin {
        modules(
            DataModule.module,
            PresentationModule.module,
        )
    }
