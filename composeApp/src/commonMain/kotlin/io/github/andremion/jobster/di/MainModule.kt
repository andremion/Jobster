package io.github.andremion.jobster.di

import io.github.andremion.jobster.data.di.DataModule
import io.github.andremion.jobster.presentation.di.PresentationModule

object MainModule {
    val modules = listOf(
        DataModule.module,
        PresentationModule.module,
    )
}
