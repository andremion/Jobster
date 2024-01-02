package io.github.andremion.jobster.data.di

import org.koin.core.module.Module

internal expect object InternalDataModule {
    val module: Module
}