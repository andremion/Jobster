package io.github.andremion.jobster.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

fun initDI(): KoinApplication =
    startKoin {
        modules(MainModule.modules)
    }
