package io.github.andremion.interprep.di

import io.github.andremion.interprep.data.di.DataModule
import io.github.andremion.interprep.presentation.di.PresentationModule

object MainModule {
    val modules = listOf(
        DataModule.module,
        PresentationModule.module,
    )
}
