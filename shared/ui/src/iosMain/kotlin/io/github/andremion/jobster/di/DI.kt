package io.github.andremion.jobster.di

import io.github.andremion.jobster.data.remote.api.GeminiApi
import org.koin.core.KoinApplication
import org.koin.dsl.module

/**
 * Initializes the DI and provides dependencies from Swift.
 * These dependencies are need because they are not multiplatform libraries.
 */
fun initDI(
    geminiApi: GeminiApi
): KoinApplication =
    initDI().modules(
        module {
            factory { geminiApi }
        }
    )
