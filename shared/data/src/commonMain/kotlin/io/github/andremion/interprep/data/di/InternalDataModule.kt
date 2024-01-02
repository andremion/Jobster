package io.github.andremion.interprep.data.di

import org.koin.core.module.Module

internal expect object InternalDataModule {
    val module: Module
}